package net.fukumaisaba.moneygive.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.fukumaisaba.moneygive.MoneyGive
import org.bukkit.entity.Player
import java.io.File
import java.sql.Connection
import java.sql.ResultSet
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

        config.connectionInitSql = "SELECT 1"

        // 接続
        hikari = HikariDataSource(hikari)
    }

    fun createTable() {

        try {

            val con = hikari!!.connection
            val prestate = con.prepareStatement("CREATE TABLE IF NOT EXISTS ? " +
                    "`uuid` VARCHAR(100) NOT NULL DEFAULT ''," +
                    "`amount` DOUBLE NOT NULL DEFAULT 0," +
                    "`updated_at` TIMESTAMP DEFAULT(DATETIME('now','localtime'))," +
                    "PRIMARY KEY (`uuid`)" +
                    ");")
            prestate.setString(1, userDataTableName)

            prestate.executeUpdate()

        }catch (e: SQLException) {
            e.printStackTrace()
        }

    }


    private fun getPlayerData(uuid: UUID): ResultSet? {
        try {
            val con = hikari!!.connection
            val prestate = con.prepareStatement("SELECT * FROM ? WHERE uuid = ?")
            prestate.setString(1, userDataTableName)
            prestate.setString(2, uuid.toString())

            return prestate.executeQuery()
        }catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    fun deletePlayerData(uuid: UUID) {
        try {
            val con = hikari!!.connection
            val prestate = con.prepareStatement("DELETE FROM ? WHERE uuid = ?")
            prestate.setString(1, userDataTableName)
            prestate.setString(2, uuid.toString())

            prestate.executeUpdate()
        }catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getPlayerGiveMoney(uuid: UUID): Double {

        val playerData = getPlayerData(uuid) ?: return 0.0

        return if (playerData.next()) {
            playerData.getDouble("amount")
        }else 0.0

    }

    fun setPlayerGiveMoney(uuid: UUID, amount: Double) {
        try {
            val con = hikari!!.connection

            val playerData = getPlayerData(uuid)
            if ((playerData != null) && playerData.next()) {
                val prestate = con.prepareStatement("UPDATE ? SET `amount` = ? WHERE `uuid` = ?")
                prestate.setString(1, userDataTableName)
                prestate.setDouble(2, amount)
                prestate.setString(3, uuid.toString())
                prestate.executeUpdate()
            }else {
                val prestate = con.prepareStatement("INSET INTO ? (`uuid`, `amount`) VALUES (?, ?)")
                prestate.setString(1, userDataTableName)
                prestate.setDouble(2, amount)
                prestate.setString(3, uuid.toString())
                prestate.executeUpdate()
            }
        }catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}