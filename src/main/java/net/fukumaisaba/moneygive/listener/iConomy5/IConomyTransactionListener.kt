package net.fukumaisaba.moneygive.listener.iConomy5

import com.iConomy.events.AccountUpdateEvent
import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.api.event.EconomyTransactionEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class IConomyTransactionListener: Listener {

    private val plugin = MoneyGive.plugin

    @EventHandler
    fun onAccountUpdate(event: AccountUpdateEvent) {

        val player = Bukkit.getOfflinePlayer(event.accountName)
        val amount = event.amount

        val ecoEvent = EconomyTransactionEvent(player, amount)

        plugin.server.pluginManager.callEvent(ecoEvent)

        if (ecoEvent.isCancelled) event.isCancelled = true

    }

}