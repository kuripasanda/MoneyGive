package net.fukumaisaba.moneygive.util

import java.sql.Timestamp
import java.util.UUID

class PlayerData(
    val exists: Boolean,
    val id: Int?,
    val uuid: UUID,
    var amount: Double,
    var updatedAt: Timestamp?,
) {
}