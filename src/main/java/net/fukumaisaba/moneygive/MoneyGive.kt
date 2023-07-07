package net.fukumaisaba.moneygive

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class MoneyGive : JavaPlugin() {

    companion object {
        lateinit var plugin: Plugin private set
    }

    override fun onEnable() {
        // 起動処理
        plugin = this

        val time = measureTimeMillis {

            // CommandAPI 連携
            CommandAPI.onLoad(CommandAPIBukkitConfig(this))
            CommandAPI.onEnable()

        }

        logger.info("MoneyGiveが起動しました (${time}ms)")

    }

    override fun onDisable() {
        // 停止処理
    }

}
