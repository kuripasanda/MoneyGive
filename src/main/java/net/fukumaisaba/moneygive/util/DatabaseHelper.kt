package net.fukumaisaba.moneygive.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.fukumaisaba.moneygive.MoneyGive
import java.io.File
import java.sql.Connection


class DatabaseHelper {

    private var hikari: HikariDataSource? = null
    private val plugin = MoneyGive.plugin

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




}