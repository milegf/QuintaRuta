package com.example.quintaruta.data.repository

import com.example.quintaruta.data.local.dao.RutaDao
import com.example.quintaruta.data.model.Ruta
import com.example.quintaruta.util.toEntity
import com.example.quintaruta.util.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RutaRepository(private val rutaDao: RutaDao) {

    val allRutas: Flow<List<Ruta>> =
        rutaDao.getAllRutas().map { list ->
            list.map { it.toModel() }
        }

    suspend fun insert(ruta: Ruta) {
        rutaDao.insert(ruta.toEntity())
    }

    suspend fun update(ruta: Ruta) {
        rutaDao.update(ruta.toEntity())
    }

    suspend fun delete(ruta: Ruta) {
        rutaDao.delete(ruta.toEntity())
    }

    fun getRutaById(id: Long): Flow<Ruta?> =
        rutaDao.getRutaById(id).map { entity ->
            entity?.toModel()
        }
}
