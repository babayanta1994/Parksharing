package ru.gross.parksharing.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey @ColumnInfo(name = "Phone") val Phone: String,
    @ColumnInfo(name = "AppID") val AppID: String,
    @ColumnInfo(name = "ID") val ID: String,
)