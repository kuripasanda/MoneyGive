package net.fukumaisaba.moneygive.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.fukumaisaba.moneygive.MoneyGive
import java.io.File
import java.sql.SQLException
import java.util.UUID


class DatabaseHelper {

    private var hikari: HikariDataSource? = null
    private val plugin = MoneyGive.plugin
    private val userDataTableName = "mg_userdata"

    init {
        // 設定など
        val dbPath = "${plugin.dataFolder.path}/sqlite.db"

        File(dbPath).parentFile.mkdirs()

        val config = HikariConfig()

        config.driverClassName = "org.sqlite.JDBC"

        config.jdbcUrl = "jdbc:sqlite:$dbPath"

        config.maximumPoolSize = 50

        config.connectionInitSql = "SELECT 1"

        // 接続
        hikari = HikariDataSource(config)

        // テーブル作成
        createTable()
    }

    private fun createTable() {

        try {

            val con = hikari!!.connection
            val prestate = con.prepareStatement("CREATE TABLE IF NOT EXISTS `${userDataTableName}` (`id` INTEGER NOT NULL, `uuid` VARCHAR(100) NOT NULL DEFAULT '', `amount` DOUBLE DEFAULT 0.0, `updated_at` TIMESTAMP DEFAULT(DATETIME('now', 'localtime')), PRIMARY KEY (`id`));")

            prestate.executeUpdate()

            con.close()
            prestate.close()

        }catch (e: SQLException) {
            e.printStackTrace()
        }

    }


    private fun getPlayerData(uuid: UUID): PlayerData? {
        try {
            val con = hikari!!.connection
            val prestate = con.prepareStatement("SELECT * FROM `$userDataTableName` WHERE uuid = ?")
            prestate.setString(1, uuid.toString())

            val result = prestate.executeQuery()

            val playerData = PlayerData(
                result.next(),
                result.getInt("id"),
                uuid,
                result.getDouble("amount"),
                result.getTimestamp("updated_at")
            )

            con.close()
            result.close()

            return playerData
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    fun deletePlayerData(uuid: UUID) {
        try {
            val con = hikari!!.connection
            val prestate = con.prepareStatement("DELETE FROM `$userDataTableName` WHERE uuid = ?")
            prestate.setString(1, uuid.toString())

            prestate.executeUpdate()

            con.close()
            prestate.close()
        }catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getPlayerGiveMoney(uuid: UUID): Double {

        val playerData = getPlayerData(uuid) ?: return 0.0

        return if (playerData.exists) {
            playerData.amount
        }else 0.0

    }

    fun setPlayerGiveMoney(uuid: UUID, amount: Double) {
        try {
            val playerData = getPlayerData(uuid)

            val con = hikari!!.connection
            if (playerData!!.exists) {
                val prestate = con.prepareStatement("UPDATE `$userDataTableName` SET `amount` = ? WHERE `uuid` = ?")
                prestate.setDouble(1, amount)
                prestate.setString(2, uuid.toString())
                prestate.executeUpdate()
                prestate.close()
            }else {
                val prestate = con.prepareStatement("INSERT INTO `$userDataTableName` (`uuid`, `amount`) VALUES (?, ?)")
                prestate.setString(1, uuid.toString())
                prestate.setDouble(2, amount)
                prestate.executeUpdate()
                prestate.close()
            }
            con.close()
        }catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun depositPlayer(uuid: UUID, amount: Double) {
        // 現段階での付与予定の金額
        val nowGiveMoney = getPlayerGiveMoney(uuid)

        // 設定
        setPlayerGiveMoney(uuid, nowGiveMoney + amount)
    }

    fun withdrawPlayer(uuid: UUID, amount: Double) {
        // 現段階での付与予定の金額
        val nowGiveMoney = getPlayerGiveMoney(uuid)

        // 設定
        setPlayerGiveMoney(uuid, nowGiveMoney - amount)
    }

}