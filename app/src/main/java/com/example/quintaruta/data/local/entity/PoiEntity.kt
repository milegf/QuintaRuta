package com.example.quintaruta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi")
data class PoiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val rutaId: Long,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    val imagenUrl: String? = null
)
