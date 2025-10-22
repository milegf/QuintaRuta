package com.example.quintaruta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruta")
data class RutaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val imagenUrl: String? = null,
    val creador: String
)
