package net.fukumaisaba.moneygive.util.message

import net.fukumaisaba.moneygive.MoneyGive

class ConfigMessage {

    private val plugin = MoneyGive.plugin
    private val message = MoneyGive.message
    private val config = plugin.config

    fun getMessage(type: ConfigMessageType): String {
        return message.getColored(config.getString("messages.${type.name}", "")!!)
    }

}