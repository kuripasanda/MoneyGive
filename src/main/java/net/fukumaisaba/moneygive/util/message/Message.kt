package net.fukumaisaba.moneygive.util.message

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Message {

    var prefix = getColored("&6[MoneyGive]&f")
        set(newPrefix) {
            field = getColored("${newPrefix}&f")
        }

    fun getColored(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    fun getReplaced(string: String, replaceTexts: HashMap<String, String>): String {
        var replacedText = string
        replaceTexts.forEach { (key, value) ->
            replacedText = replacedText.replace(key, value)
        }

        return replacedText
    }


    fun sendMessage(sender: CommandSender, needPrefix: Boolean, msg: String) {
        var prefix_ = ""
        if (needPrefix) prefix_ = "$prefix "

        sender.sendMessage(getColored("${prefix_}${msg}"))
    }
    fun sendMessage(player: Player, needPrefix: Boolean, msg: String) {
        var prefix_ = ""
        if (needPrefix) prefix_ = "$prefix "

        player.sendMessage(getColored("${prefix_}${msg}"))
    }

}