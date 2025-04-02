package org.afterlike.polaris.events

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.input.Mouse

object ForgeEventBridge {
    private val mc = Minecraft.getMinecraft()
    private val lastMouseState = BooleanArray(8) { false }

    @SubscribeEvent
    fun onWorldJoin(event: EntityJoinWorldEvent) {
        if (event.entity === mc.thePlayer && event.world.isRemote) {
            EventManager.call(OnWorldJoin())
            println("OnWorldJoin")
        }
    }

    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent) {
        val name = event.gui?.javaClass?.simpleName ?: "null"
        val opened = event.gui != null
        EventManager.call(OnGuiUpdate(name, opened))
        println("OnGuiUpdate $name")
    }

    @SubscribeEvent
    fun onRender(event: RenderGameOverlayEvent.Text) {
        EventManager.call(OnRenderTick(event.partialTicks))
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            for (button in 0..7) {
                val isDown = Mouse.isButtonDown(button)
                if (isDown && !lastMouseState[button]) {
                    EventManager.call(OnMouse(button, true))
                }
                lastMouseState[button] = isDown
            }
        }
    }

    @SubscribeEvent
    fun onPlayerTick(event: TickEvent.PlayerTickEvent) {
        if (event.player != Minecraft.getMinecraft().thePlayer || event.phase != TickEvent.Phase.START) return
        EventManager.call(OnPreUpdate())

        val p = event.player
        EventManager.call(
            OnPreMotion(
                PlayerState(
                    x = p.posX,
                    y = p.posY,
                    z = p.posZ,
                    yaw = p.rotationYaw,
                    pitch = p.rotationPitch,
                    onGround = p.onGround,
                    isSprinting = p.isSprinting,
                    isSneaking = p.isSneaking
                )
            )
        )

        EventManager.call(OnPostPlayerInput())
        EventManager.call(OnPostMotion())
    }
    
    @SubscribeEvent
    fun onClientChatReceived(event: ClientChatReceivedEvent) {
        val chatEvent = OnChatEvent(event.message, event.type.toInt())
        EventManager.call(chatEvent)
        
        if (chatEvent.isCanceled && chatEvent.isCancelable) {
            event.isCanceled = true
        }
    }
}