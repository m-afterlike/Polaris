package org.afterlike.polaris.modules.test

import org.afterlike.polaris.events.OnWorldJoin
import org.afterlike.polaris.events.Subscribe
import org.afterlike.polaris.utils.Player

class TestModule {
    @Subscribe
    fun onWorldJoin(event: OnWorldJoin) {
        println("OnWorldJoin")
        Player.sendMessage("§aJoined a world")
    }
}