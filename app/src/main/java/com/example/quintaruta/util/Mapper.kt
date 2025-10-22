package com.example.quintaruta.util

import com.example.quintaruta.data.local.entity.PoiEntity
import com.example.quintaruta.data.local.entity.RutaEntity
import com.example.quintaruta.data.model.Poi
import com.example.quintaruta.data.model.Ruta

fun RutaEntity.toModel() = Ruta(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    categoria = categoria,
    imagenUrl = imagenUrl,
    creador = creador
)

fun Ruta.toEntity() = RutaEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    categoria = categoria,
    imagenUrl = imagenUrl,
    creador = creador
)

fun PoiEntity.toModel() = Poi(
    id = id,
    rutaId = rutaId,
    nombre = nombre,
    descripcion = descripcion,
    latitud = latitud,
    longitud = longitud,
    imagenUrl = imagenUrl
)

fun Poi.toEntity() = PoiEntity(
    id = id,
    rutaId = rutaId,
    nombre = nombre,
    descripcion = descripcion,
    latitud = latitud,
    longitud = longitud,
    imagenUrl = imagenUrl
)