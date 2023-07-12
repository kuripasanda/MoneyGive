package net.fukumaisaba.moneygive.listener

import com.djrapitops.vaultevents.events.economy.PlayerDepositEvent
import com.djrapitops.vaultevents.events.economy.PlayerWithdrawEvent
import net.fukumaisaba.moneygive.MoneyGive
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class VaultTransactionListener: Listener {

    private val api = MoneyGive.api
    private val vaultHook = MoneyGive.vaultHook

    @EventHandler
    fun onPlayerDeposit(event: PlayerDepositEvent) {

        val player = event.offlinePlayer
        val amount = event.amount

        // オフラインなら
        if (!player.isOnline) {
            // キャンセル
            vaultHook.giveMoney(player, -amount)

            // 付与
            api.depositPlayer(player, amount)
        }

    }

    @EventHandler
    fun onPlayerWithdraw(event: PlayerWithdrawEvent) {

        val player = event.offlinePlayer
        val amount = event.amount

        // オフラインなら
        if (!player.isOnline) {
            // キャンセル
            vaultHook.giveMoney(player, -amount)

            // 付与
            api.withdrawPlayer(player, amount)
        }

    }

}