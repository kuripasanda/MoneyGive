package net.fukumaisaba.moneygive.listener.moneyGive

import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.api.event.EconomyTransactionEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EconomyTransactionListener: Listener {

    @EventHandler
    fun onEconomyTransaction(event: EconomyTransactionEvent) {

        val api = MoneyGive.api

        val player = event.player
        val amount = event.amount

        // (恐らく)Towny系の請求であれば(町や国への請求であれば)スルー
        if (player.name?.startsWith("towny-") == true) return
        if (player.name?.startsWith("town-") == true) return
        if (player.name?.startsWith("nation-") == true) return

        // オフラインの場合
        if (!player.isOnline) {
            event.isCancelled = true
            api.addGiveBalance(player, amount)
        }

    }

}