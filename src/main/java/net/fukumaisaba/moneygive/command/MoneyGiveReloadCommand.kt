package net.fukumaisaba.moneygive.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.message.Message

class MoneyGiveReloadCommand {

    fun register() {

        CommandAPICommand("moneygivereload")
            .withPermission("moneygive.commands.reload")
            .executes(CommandExecutor { sender, _ ->
                MoneyGive.reload()
                Message.sendMessage(sender, true, "&a設定を再読込しました！")
            })
            .register()

    }

}