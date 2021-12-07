package ru.gross.parksharing.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gross.parksharing.db.dao.UserDao
import ru.gross.parksharing.db.entity.User


@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class ParkDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: ParkDatabase? = null
        fun getDatabase(context: Context): ParkDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkDatabase::class.java,
                    "parking_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}