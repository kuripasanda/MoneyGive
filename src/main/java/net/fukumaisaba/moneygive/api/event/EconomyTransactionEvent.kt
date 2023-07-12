package net.fukumaisaba.moneygive.api.event

import org.bukkit.OfflinePlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EconomyTransactionEvent(
    val player: OfflinePlayer,
    val amount: Double,
): Event() {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList { return handlers }
    }

    override fun getHandlers(): HandlerList { return getHandlerList() }

    var isCancelled: Boolean = false

}