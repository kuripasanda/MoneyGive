package net.fukumaisaba.moneygive.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.message.Message

class MoneyGiveReloadCommand {

    private val message = MoneyGive.message

    fun register() {

        CommandAPICommand("moneygivereload")
            .withPermission("moneygive.commands.reload")
            .executes(CommandExecutor { sender, _ ->
                MoneyGive.reload()
                message.sendMessage(sender, true, "&a設定を再読込しました！")
            })
            .register()

    }

}