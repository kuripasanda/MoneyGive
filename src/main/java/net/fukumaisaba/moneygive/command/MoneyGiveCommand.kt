package net.fukumaisaba.moneygive.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.DoubleArgument
import dev.jorel.commandapi.arguments.OfflinePlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import net.fukumaisaba.moneygive.MoneyGive
import net.fukumaisaba.moneygive.util.DatabaseHelper
import net.fukumaisaba.moneygive.util.Message
import org.bukkit.OfflinePlayer

class MoneyGiveCommand {

    private val vaultEconomy = MoneyGive.vaultEconomy

    fun register(dbHelper: DatabaseHelper) {

        CommandAPICommand("moneygive")
            .withArguments(OfflinePlayerArgument("player"))
            .withArguments(DoubleArgument("amount"))
            .executes(CommandExecutor { sender, args ->

                val player = args.get(0) as OfflinePlayer // 付与したいプレイヤー
                val amount = args.get(1) as Double // 付与したい金額

                val uuid = player.uniqueId

                // 演出
                Message.sendMessage(sender, true, "&aプレイヤーに&e${amount}円を付与しました")

                // プレイヤーがオンラインか
                if (player.isOnline) {
                    vaultEconomy.depositPlayer(player, amount)
                }else {
                    // 現在の付与する金額
                    val nowGiveAmount = dbHelper.getPlayerGiveMoney(uuid)

                    // 付与する金額を設定
                    dbHelper.setPlayerGiveMoney(uuid, (nowGiveAmount + amount))
                }

            })
            .register()

    }

}