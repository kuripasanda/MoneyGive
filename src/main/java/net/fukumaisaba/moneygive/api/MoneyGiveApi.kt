package net.fukumaisaba.moneygive.api

import org.bukkit.OfflinePlayer

interface MoneyGiveApi {

    fun getGiveBalance(player: OfflinePlayer): Double

    fun setGiveBalance(player: OfflinePlayer, amount: Double)

    fun depositPlayer(player: OfflinePlayer, amount: Double)

    fun withdrawPlayer(player: OfflinePlayer, amount: Double)

}