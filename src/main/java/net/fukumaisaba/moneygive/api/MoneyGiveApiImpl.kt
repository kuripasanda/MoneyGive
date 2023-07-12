package net.fukumaisaba.moneygive.api

import net.fukumaisaba.moneygive.util.DatabaseHelper
import org.bukkit.OfflinePlayer

class MoneyGiveApiImpl(private val dbHelper: DatabaseHelper): MoneyGiveApi {

    override fun getGiveBalance(player: OfflinePlayer): Double {
        return dbHelper.getPlayerGiveMoney(player.uniqueId)
    }

    override fun setGiveBalance(player: OfflinePlayer, amount: Double) {
        dbHelper.setPlayerGiveMoney(player.uniqueId, amount)
    }

    override fun addGiveBalance(player: OfflinePlayer, amount: Double) {
        val nowGiveBalance = getGiveBalance(player)
        setGiveBalance(player, (nowGiveBalance + amount))
    }

    override fun depositPlayer(player: OfflinePlayer, amount: Double) {
        dbHelper.depositPlayer(player.uniqueId, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double) {
        dbHelper.withdrawPlayer(player.uniqueId, amount)
    }

    override fun deletePlayerData(player: OfflinePlayer) {
        dbHelper.deletePlayerData(player.uniqueId)
    }


}