package com.batikfy.batikfy.data.local.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batikfy.batikfy.data.local.entity.ArticleEntity
import com.batikfy.batikfy.data.local.entity.BatikEntity

@Dao
interface BatikfyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatik(batik: List<BatikEntity>){
        Log.d("BatikfyDao", "Inserting batik: $batik")
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: List<ArticleEntity>){
        Log.d("BatikfyDao", "Inserting article: $article")
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneBatik(batik: BatikEntity){
        Log.d("BatikfyDao", "Inserting 1 batik: ${batik.name}")
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneArticle(article:ArticleEntity){
        Log.d("BatikfyDao", "Inserting 1 article: ${article.title}")
    }

    @Query("SELECT * FROM batiks")
    fun getAllBatik(): LiveData<List<BatikEntity>>

    @Query("SELECT * FROM articles")
    fun getAllArticle(): LiveData<List<ArticleEntity>>

    @Query("SELECT * FROM batiks where bookmarked = 1")
    fun getBookmarkedBatik(): LiveData<List<BatikEntity>>

    @Query("DELETE FROM batiks WHERE bookmarked = 0")
    fun deleteAllBatik()

    @Query("SELECT EXISTS (SELECT 1 FROM batiks WHERE id=:id AND bookmarked=1)")
    fun isBatikBookmarkedById(id: String): Boolean

    @Query("UPDATE batiks SET bookmarked = :bookmarked WHERE id = :id")
    suspend fun setBatikBookmarkedById(id: String, bookmarked: Boolean)

    @Query("SELECT EXISTS (SELECT 1 FROM batiks WHERE id=:id)")
    suspend fun isBatikDataExists(id: String): Boolean
}