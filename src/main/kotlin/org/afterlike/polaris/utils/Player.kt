package org.afterlike.polaris.utils

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object Player {
    private val mc: Minecraft
        get() = Minecraft.getMinecraft()

    fun sendMessage(message: String, prefix: Boolean = true) {
        val formatted = if (prefix) "§7[§dP§7] §f$message" else message
        println("sendMessage() called, thePlayer = ${mc.thePlayer}")
        mc.thePlayer?.addChatMessage(ChatComponentText(formatted))
    }
}