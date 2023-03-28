package com.technomatesoftware.ergostats.domain.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `summary_metrics` " +
                    "(`summary_metric_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "`label` TEXT NOT NULL, " +
                    "`current` INTEGER NOT NULL, " +
                    "`is_absolute` INTEGER NOT NULL, " +
                    "`is_relative` INTEGER NOT NULL, " +
                    "`is_utxos` INTEGER NOT NULL, " +
                    "`is_volume` INTEGER NOT NULL, " +
                    "`is_transactions` INTEGER NOT NULL, " +
                    "`is_miners` INTEGER NOT NULL, " +
                    "`is_contracts` INTEGER NOT NULL, " +
                    "`is_p2pks` INTEGER NOT NULL, " +
                    "`diff_1d` INTEGER NOT NULL, " +
                    "`diff_1w` INTEGER NOT NULL, " +
                    "`diff_4w` INTEGER NOT NULL, " +
                    "`diff_6m` INTEGER NOT NULL, " +
                    "`diff_1y` INTEGER NOT NULL)"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `token_supply` " +
                    "(`token_supply_id` INTEGER NOT NULL PRIMARY KEY, " +
                    "`circulating_supply` REAL NOT NULL)"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `metrics_chart_data` " +
                    "(`metrics_chart_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "`date` INTEGER NOT NULL, " +
                    "`value` INTEGER NOT NULL, " +
                    "`is_p2pk` INTEGER NOT NULL, " +
                    "`is_contract` INTEGER NOT NULL, " +
                    "`is_mining` INTEGER NOT NULL, " +
                    "`is_volume` INTEGER NOT NULL, " +
                    "`is_transaction` INTEGER NOT NULL, " +
                    "`is_utxo` INTEGER NOT NULL)"
        )
    }
}
