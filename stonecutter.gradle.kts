import dev.kikugie.stonecutter.build.config.ReplacementContainer.StringReplacementSpec

plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3" /* [SC] DO NOT EDIT */

stonecutter tasks {
    order("publishModrinth")
    order("publishCurseforge")
}

stonecutter parameters {
    replacements {
        string(current.parsed eq "1.8.9") {
            replace("ClientLevel", "ClientWorld")
            replace("getRainLevel", "getRain")
            replace("getThunderLevel", "getThunder")
            replacePath("net.minecraft.client.multiplayer.ClientLevel", "net.minecraft.client.world.ClientWorld")
            replacePath("net.minecraft.world.level.Level", "net.minecraft.world.World")
            replacePath("net.minecraft.world.level.biome.Biome", "net.minecraft.world.biome.Biome")
        }

        regex(current.parsed eq "1.8.9") {
            replace(
                """\bLevel\b""", """World""",
                """\bWorld\b""", """Level""",
            )
        }
    }
}

private fun StringReplacementSpec.replacePath(from: String, to: String) {
    replace(from, to)
    replace(from.replace('.', '/'), to.replace('.', '/'))
}
