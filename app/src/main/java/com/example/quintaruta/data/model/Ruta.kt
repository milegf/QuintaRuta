package com.quintaruta.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutas")
data class Ruta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val imagenUrl: String? = null
)
