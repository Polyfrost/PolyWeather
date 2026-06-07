import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("dev.kikugie.loom-back-compat")
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("dev.deftu.gradle.bloom") version "0.2.0"
    id("me.modmuss50.mod-publish-plugin") version "1.1.0"
}

val modid = property("mod.id")
val modname = property("mod.name")
val modversion = property("mod.version")
val mcversion = stonecutter.current.version
val oneconfigversion = property("oneconfig_version")

version = "$modversion+$mcversion"
base.archivesName = modname.toString()

repositories {
    maven("https://maven.parchmentmc.org")
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
    maven("https://maven.gegy.dev/releases")
    maven("https://maven.logix.dev/snapshots")
    maven("https://nexus.prsm.wtf/repository/maven-public/maven-repo/releases/")
    maven("https://repo.hypixel.net/repository/Hypixel/")
    maven("https://maven.deftu.dev/releases")
    maven("https://maven.fabricmc.net/releases")
    maven("https://jitpack.io") { content { includeGroupAndSubgroups("com.github") } }
    maven("https://maven.bawnorton.com/releases") { content { includeGroup("com.github.bawnorton.mixinsquared") } }
    maven("https://maven.azureaaron.net/releases") { content { includeGroup("net.azureaaron") } }
    maven("https://redirector.kotlinlang.org/maven/compose-dev")
    mavenCentral()
    google()
    gradlePluginPortal()
}

loom {
    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
        runDir = "../../run" // This sets the run folder for all mc versions to the same folder. Remove this line if you want individual run folders.
    }

    runConfigs.remove(runConfigs["server"]) // Removes server run configs
}

dependencies {
    minecraft("com.mojang:minecraft:$mcversion")

    val hasOfficialMappings = findProperty("has_official_mappings")?.toString()?.toBoolean() ?: true
    if (hasOfficialMappings) {
        @Suppress("UnstableApiUsage")
        mappings(loom.layered {
            officialMojangMappings()
            optionalProp("${property("parchment_version")}") {
                parchment("org.parchmentmc.data:parchment-${property("minecraft_version")}:$it@zip")
            }
            optionalProp("${property("yalmm_version")}") {
                mappings("dev.lambdaurora:yalmm-mojbackward:${property("minecraft_version")}+build.$it")
            }
        })
    } else {
        findProperty("mappings_version")?.toString()?.takeUnless { it.isBlank() }?.let {
            mappings(it)
        }
    }
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("org.polyfrost.oneconfig:$mcversion-fabric:$oneconfigversion")
    for (module in arrayOf("config", "config-impl", "internal", "ui")) {
        implementation("org.polyfrost.oneconfig:$module:$oneconfigversion")
    }
}

bloom {
    replacement("@MOD_ID@", modid!!)
    replacement("@MOD_NAME@", modname!!)
    replacement("@MOD_VERSION@", modversion!!)
}

tasks.processResources {
    val props = mapOf(
        "mod_id" to modid,
        "mod_name" to modname,
        "mod_version" to modversion,
        "mc_version" to mcversion,
        "loader_version" to providers.gradleProperty("loader_version").get()
    )

    inputs.properties(props)

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

val javaVersion = findProperty("java_version")?.toString()?.toIntOrNull() ?: 21
val javaVersionEnum = JavaVersion.toVersion(javaVersion)
val jvmTarget = JvmTarget.fromTarget(javaVersion.toString())

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaVersion)
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(jvmTarget)
}

java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
    sourceCompatibility = javaVersionEnum
    targetCompatibility = javaVersionEnum
}

tasks.jar {
    inputs.property("archivesName", base.archivesName)

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}

fun <T> optionalProp(property: String, block: (String) -> T?): T? =
    findProperty(property)?.toString()?.takeUnless { it.isBlank() }?.let(block)

val modrinthId = findProperty("publish.modrinth")?.toString()?.takeIf { it.isNotBlank() }

// make sure modrinth.token is set in your user gradle properties
publishMods {
    file = if (stonecutter.current.parsed >= "26.1") {
        project.tasks.jar.get().archiveFile
    } else {
        project.tasks.named<org.gradle.api.tasks.bundling.AbstractArchiveTask>("remapJar").flatMap { it.archiveFile }
    }

    displayName = modversion.toString()
    version = "v$modversion"
    changelog =
        project.rootProject.file("CHANGELOG.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."
    type = ALPHA

    modLoaders.add("fabric")

    dryRun = modrinthId == null

    if (modrinthId != null) {
        modrinth {
            projectId = property("publish.modrinth").toString()
            accessToken = findProperty("modrinth.token").toString()

            minecraftVersions.add(mcversion)

            requires("oneconfig")
        }
    }
}