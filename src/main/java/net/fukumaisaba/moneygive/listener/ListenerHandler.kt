package net.fukumaisaba.moneygive.listener

import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.listener.iConomy5.IConomyTransactionListener
import net.fukumaisaba.moneygive.listener.moneyGive.EconomyTransactionListener
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class ListenerHandler(private val plugin: Plugin, private val instance: MoneyGive) {

    val iConomyListeners: MutableList<Listener> = mutableListOf()

    fun registerBasicListeners() {

        /**
         * SpigotAPI
         */
        registerListener(PlayerJoinListener())
        registerListener(HookPluginListener(this, instance))

        /**
         * MoneyGive
         */
        registerListener(EconomyTransactionListener())

    }

    fun registerIConomyListeners() {
        iConomyListeners.add(IConomyTransactionListener())

        registerListeners(iConomyListeners)
    }


    fun registerListener(listener: Listener) {
        plugin.server.pluginManager.registerEvents(listener, plugin)
    }
    fun registerListeners(listeners: MutableList<Listener>) {
        for (listener in listeners) registerListener(listener)
    }

}