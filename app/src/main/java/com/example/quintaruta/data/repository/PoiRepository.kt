package com.example.quintaruta.data.repository

import com.example.quintaruta.data.local.dao.PoiDao
import com.example.quintaruta.data.model.Poi
import com.example.quintaruta.util.toEntity
import com.example.quintaruta.util.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PoiRepository(private val poiDao: PoiDao) {

    fun getPoisByRuta(routeId: Long): Flow<List<Poi>> =
        poiDao.getPoisByRuta(routeId).map { it.map { entity -> entity.toModel() } }

    fun getPoiById(id: Long): Flow<Poi?> =
        poiDao.getPoiById(id).map { entity ->
            entity?.toModel()
        }

    suspend fun insert(poi: Poi) {
        poiDao.insert(poi.toEntity())
    }

    suspend fun update(poi: Poi) {
        poiDao.update(poi.toEntity())
    }

    suspend fun delete(poi: Poi) {
        poiDao.delete(poi.toEntity())
    }
}
