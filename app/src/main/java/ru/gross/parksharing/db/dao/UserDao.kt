package ru.gross.parksharing.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.gross.parksharing.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}