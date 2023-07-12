package net.fukumaisaba.moneygive.listener

import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.VaultHook
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginEnableEvent

class HookPluginListener(
    private val listenerHandler: ListenerHandler,
    private val instance: MoneyGive,
): Listener {

    @EventHandler
    fun onPluginEnable(event: PluginEnableEvent) {

        val moneyGivePlugin = MoneyGive.plugin

        val plugin = event.plugin

        var isHook = false
        when (plugin.name) {
            "iConomy" -> {
                moneyGivePlugin.logger.info("iConomyと連携しました！")
                listenerHandler.registerIConomyListeners()
                isHook = true
            }
        }

        if (isHook) instance.setVaultHook(VaultHook())

    }

}