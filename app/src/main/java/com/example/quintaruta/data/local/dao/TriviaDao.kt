package com.example.quintaruta.data.local.dao

import com.example.quintaruta.data.local.entity.TriviaEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrivia(trivia: TriviaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trivias: List<TriviaEntity>)

    @Query("SELECT * FROM trivias ORDER BY id ASC")
    fun getAllTrivias(): Flow<List<TriviaEntity>>

    @Query("SELECT * FROM trivias WHERE poi_id = :poiId ORDER BY id ASC")
    fun getTriviasByPoi(poiId: Long): Flow<List<TriviaEntity>>

    @Update
    suspend fun updateTrivia(trivia: TriviaEntity)

    @Delete
    suspend fun deleteTrivia(trivia: TriviaEntity)

    @Query("DELETE FROM trivias")
    suspend fun deleteAll()

    // (!) METODO EXCLUSIVO PARA EL SEEDER
    @Query("SELECT * FROM trivias")
    suspend fun getAllTriviasList(): List<TriviaEntity>
}