package org.afterlike.polaris.events

import net.minecraft.util.IChatComponent

/**
 * Event fired when a chat message is received
 */
class OnChatEvent(
    val component: IChatComponent,
    val type: Int, // 0: regular chat, 1: system message, 2: action bar
    // Whether this event is cancelable
    var isCancelable: Boolean = true,
    // Whether this event is canceled
    var isCanceled: Boolean = false
) 