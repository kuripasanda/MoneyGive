package net.fukumaisaba.moneygive.util

import net.fukumaisaba.moneygive.MoneyGive
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer

class VaultHook {

    private lateinit var vaultEconomy: Economy
    private val plugin = MoneyGive.plugin


    init {
        setupEconomy()
    }

    private fun setupEconomy(): Boolean {
        if (plugin.server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = plugin.server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false
        vaultEconomy = rsp.provider
        return true
    }


    fun getBalance(player: OfflinePlayer): Double { return vaultEconomy.getBalance(player) }

    fun giveMoney(player: OfflinePlayer, amount: Double): EconomyResponse {
        if (amount < 0) return withdrawPlayer(player, -amount)
        return depositPlayer(player, amount)
    }

    fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return vaultEconomy.depositPlayer(player, amount)
    }

    fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return vaultEconomy.withdrawPlayer(player, amount)
    }

}