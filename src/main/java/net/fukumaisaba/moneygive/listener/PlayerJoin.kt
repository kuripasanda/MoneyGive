package net.fukumaisaba.moneygive.listener

import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.DatabaseHelper
import net.fukumaisaba.moneygive.util.message.ConfigMessage
import net.fukumaisaba.moneygive.util.message.ConfigMessageType
import net.fukumaisaba.moneygive.util.message.Message
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(val dbHelper: DatabaseHelper): Listener {

    private val plugin = MoneyGive.plugin
    private val vaultEconomy = MoneyGive.vaultEconomy

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        val player = event.player
        val uuid = player.uniqueId

        val nowGiveAmount = dbHelper.getPlayerGiveMoney(uuid)

        if (nowGiveAmount > 0.0) {
            dbHelper.deletePlayerData(uuid)
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                vaultEconomy.depositPlayer(player, nowGiveAmount)

                // 演出
                val replaceTexts = HashMap<String, String>()
                replaceTexts["%player%"] = player.name
                replaceTexts["%money%"] = nowGiveAmount.toString()
                replaceTexts["%moneyUnit%"] = ConfigMessage().getMessage(ConfigMessageType.MONEY_UNIT)
                Message.sendMessage(player, true,
                    Message.getReplaced(ConfigMessage().getMessage(ConfigMessageType.MONEY_GET_OFFLINE), replaceTexts))
            }, plugin.config.getLong("config.giveMoneyWaitTick", 20L))
        }

    }

}