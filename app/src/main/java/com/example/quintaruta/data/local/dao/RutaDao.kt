package com.example.quintaruta.data.local.dao

import androidx.room.*
import com.example.quintaruta.data.local.entity.RutaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutaEntity: RutaEntity)

    @Update
    suspend fun update(rutaEntity: RutaEntity)

    @Delete
    suspend fun delete(rutaEntity: RutaEntity)

    @Query("SELECT * FROM ruta")
    fun getAllRutas(): Flow<List<RutaEntity>>

    @Query("SELECT * FROM ruta WHERE id = :id")
    fun getRutaById(id: Long): Flow<RutaEntity?>

    @Insert
    suspend fun insertAll(rutas: List<RutaEntity>)

    // (!) METODO EXCLUSIVO PARA EL SEEDER
    @Query("SELECT * FROM ruta")
    suspend fun getAllRutasList(): List<RutaEntity>
}
