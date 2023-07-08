package net.fukumaisaba.moneygive.util.message

import net.fukumaisaba.moneygive.MoneyGive

class ConfigMessage {

    private val plugin = MoneyGive.plugin
    private val config = plugin.config

    fun getMessage(type: ConfigMessageType): String {
        return Message.getColored(config.getString("messages.${type.name}", "")!!)
    }

}