package net.fukumaisaba.moneygive

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.fukumaisaba.moneygive.api.MoneyGiveApi
import net.fukumaisaba.moneygive.api.MoneyGiveApiImpl
import net.fukumaisaba.moneygive.command.MoneyGiveCommand
import net.fukumaisaba.moneygive.command.MoneyGiveReloadCommand
import net.fukumaisaba.moneygive.listener.PlayerJoin
import net.fukumaisaba.moneygive.util.DatabaseHelper
import net.fukumaisaba.moneygive.util.message.Message
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis


class MoneyGive : JavaPlugin() {

    companion object {
        lateinit var plugin: Plugin private set
        lateinit var vaultEconomy: Economy private set
        lateinit var message: Message private set

        lateinit var api: MoneyGiveApi private set

        private lateinit var dbHelper: DatabaseHelper

        fun reload() {
            plugin.saveDefaultConfig()
            plugin.reloadConfig()

            message.prefix = plugin.config.getString("messages.PREFIX", "&6[MoneyGive]&f")!!
        }
    }

    override fun onEnable() {
        // 起動処理
        plugin = this

        val time = measureTimeMillis {

            // 設定ファイル
            saveDefaultConfig()

            // メッセージ関連
            message = Message()

            // データベース関連
            dbHelper = DatabaseHelper()

            // VaultAPI 連携
            setupEconomy()

            // CommandAPI 連携
            CommandAPI.onLoad(CommandAPIBukkitConfig(this))
            CommandAPI.onEnable()

            // コマンド登録
            MoneyGiveCommand().register(dbHelper)
            MoneyGiveReloadCommand().register()

            // リスナー登録
            server.pluginManager.registerEvents(PlayerJoin(dbHelper), this)

            // API
            api = MoneyGiveApiImpl(dbHelper)

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
