package com.example.quintaruta.data.local.dao

import androidx.room.*
import com.example.quintaruta.data.local.entity.PoiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(poi: PoiEntity)

    @Update
    suspend fun update(poi: PoiEntity)

    @Delete
    suspend fun delete(poi: PoiEntity)

    @Query("SELECT * FROM poi WHERE rutaId = :rutaId")
    fun getPoisByRuta(rutaId: Long): Flow<List<PoiEntity>>

    @Query("SELECT * FROM poi WHERE id = :id")
    fun getPoiById(id: Long): Flow<PoiEntity?>
}
