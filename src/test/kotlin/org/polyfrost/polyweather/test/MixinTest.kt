package org.polyfrost.polyweather.test

import net.minecraft.SharedConstants
import net.minecraft.server.Bootstrap
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.MixinEnvironment.Option
import org.spongepowered.asm.mixin.transformer.IMixinTransformer

/**
 * Audits mixins without launching a full Minecraft client
 * Inspired by [Skyblocker](https://github.com/SkyblockerMod/Skyblocker)
 */
class MixinTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupEnvironment() {
            SharedConstants.tryDetectVersion()
            Bootstrap.bootStrap()
        }
    }

    @Test
    fun `mixins load successfully`() {
        val environment = MixinEnvironment.getCurrentEnvironment()
        Assertions.assertInstanceOf(
            IMixinTransformer::class.java,
            environment.activeTransformer,
        )
        // Dev refmap remapping retries failed selectors without the descriptor
        // Production does not, so disable it or bad selectors still pass the audit
        environment.setOption(Option.REFMAP_REMAP, false)
        environment.audit()
    }
}
