package net.fukumaisaba.moneygive.util

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Message {

    var prefix = getColored("&6[MoneyGive]&f")
        set(newPrefix) {
            field = getColored("${newPrefix}&f")
        }

    fun getColored(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }


    fun sendMessage(sender: CommandSender, needPrefix: Boolean, msg: String) { sendMessage(sender, needPrefix, msg) }
    fun sendMessage(player: Player, needPrefix: Boolean, msg: String) {
        var prefix_ = ""
        if (needPrefix) prefix_ = "$prefix "

        player.sendMessage(getColored("${prefix_}${msg}"))
    }

}