package net.fukumaisaba.moneygive

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.fukumaisaba.moneygive.util.DatabaseHelper
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis


class MoneyGive : JavaPlugin() {

    companion object {
        lateinit var plugin: Plugin private set
        lateinit var vaultEconomy: Economy private set
    }

    override fun onEnable() {
        // 起動処理
        plugin = this

        val time = measureTimeMillis {

            // データベース関連
            val dbHelper = DatabaseHelper()

            // VaultAPI 連携
            setupEconomy()

            // CommandAPI 連携
            CommandAPI.onLoad(CommandAPIBukkitConfig(this))
            CommandAPI.onEnable()

        }

        logger.info("MoneyGiveが起動しました (${time}ms)")

    }

    override fun onDisable() {
        // 停止処理
    }


    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false
        vaultEconomy = rsp.provider
        return true
    }

}
