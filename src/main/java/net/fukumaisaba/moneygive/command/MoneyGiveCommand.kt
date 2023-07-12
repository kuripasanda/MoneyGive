package net.fukumaisaba.moneygive.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.DoubleArgument
import dev.jorel.commandapi.arguments.OfflinePlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.DatabaseHelper
import net.fukumaisaba.moneygive.util.message.ConfigMessage
import net.fukumaisaba.moneygive.util.message.ConfigMessageType
import net.fukumaisaba.moneygive.util.message.Message
import org.bukkit.OfflinePlayer

class MoneyGiveCommand {

    private val vaultEconomy = MoneyGive.vaultEconomy
    private val message = MoneyGive.message

    fun register(dbHelper: DatabaseHelper) {

        CommandAPICommand("moneygive")
            .withArguments(OfflinePlayerArgument("player"))
            .withArguments(DoubleArgument("amount"))
            .executes(CommandExecutor { sender, args ->

                val player = args.get(0) as OfflinePlayer // 付与したいプレイヤー
                val amount = args.get(1) as Double // 付与したい金額

                if (player.name == null) {
                    message.sendMessage(sender, true, "&cそのプレイヤーは存在しません！")
                    return@CommandExecutor
                }

                val uuid = player.uniqueId

                // 演出
                val replaceTexts = HashMap<String, String>()
                replaceTexts["%player%"] = player.name!!
                replaceTexts["rimitter"] = sender.name
                replaceTexts["%money%"] = amount.toString()
                replaceTexts["%moneyUnit%"] = ConfigMessage().getMessage(ConfigMessageType.MONEY_UNIT)
                message.sendMessage(sender, true,
                    message.getReplaced(ConfigMessage().getMessage(ConfigMessageType.MONEY_GIVE_SUCCESS), replaceTexts))

                // プレイヤーがオンラインか
                if (player.isOnline) {
                    vaultEconomy.depositPlayer(player, amount)

                    // 演出
                    message.sendMessage(player.player!!, true,
                        message.getReplaced(ConfigMessage().getMessage(ConfigMessageType.MONEY_GET), replaceTexts))
                }else {
                    dbHelper.depositPlayer(uuid, amount)
                }

            })
            .register()

    }

}