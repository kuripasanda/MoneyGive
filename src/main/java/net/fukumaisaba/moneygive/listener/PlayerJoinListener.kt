package net.fukumaisaba.moneygive.listener

import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.message.ConfigMessage
import net.fukumaisaba.moneygive.util.message.ConfigMessageType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.math.ceil

class PlayerJoinListener: Listener {

    private val plugin = MoneyGive.plugin
    private val message = MoneyGive.message

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        val api = MoneyGive.api
        val vaultHook = MoneyGive.vaultHook

        val player = event.player

        val nowGiveAmount = api.getGiveBalance(player)

        if (nowGiveAmount > 0.0) {
            api.deletePlayerData(player)
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                vaultHook.depositPlayer(player, nowGiveAmount)

                // 演出
                val replaceTexts = HashMap<String, String>()
                replaceTexts["%player%"] = player.name
                replaceTexts["%money%"] = message.formatMoney(nowGiveAmount).toString()
                replaceTexts["%moneyUnit%"] = ConfigMessage().getMessage(ConfigMessageType.MONEY_UNIT)
                message.sendMessage(player, true,
                    message.getReplaced(ConfigMessage().getMessage(ConfigMessageType.MONEY_GET_OFFLINE), replaceTexts))
            }, plugin.config.getLong("config.giveMoneyWaitTick", 20L))
        }

    }

}