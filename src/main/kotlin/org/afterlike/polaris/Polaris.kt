package org.afterlike.polaris

import org.afterlike.polaris.events.ForgeEventBridge

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.common.MinecraftForge
import org.afterlike.polaris.events.EventManager
import org.afterlike.polaris.modules.anticheat.AnticheatModule
import org.afterlike.polaris.modules.test.TestModule

@Mod(modid = "polaris", useMetadata = true)
class Polaris {
    companion object {
        lateinit var INSTANCE: Polaris
            private set
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        INSTANCE = this
        println("Initialized Polaris")
        MinecraftForge.EVENT_BUS.register(ForgeEventBridge)

        EventManager.register(TestModule())
        EventManager.register(AnticheatModule())
    }
}