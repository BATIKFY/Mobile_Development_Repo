package com.batikfy.batikfy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batikfy.batikfy.data.local.entity.BatikEntity

@Dao
interface BatikfyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatik(batik: BatikEntity)

    @Query("SELECT * FROM batiks")
    fun getAllBatik(): List<BatikEntity>
}