package com.example.quintaruta.util

import com.example.quintaruta.data.local.entity.PoiEntity
import com.example.quintaruta.data.local.entity.RutaEntity
import com.example.quintaruta.data.local.entity.TriviaEntity
import com.example.quintaruta.data.model.Poi
import com.example.quintaruta.data.model.Ruta
import com.example.quintaruta.data.model.Trivia

// MAPPERS DE RUTA
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

// MAPPERS DE POIs
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

// MAPPERS DE TRIVIA
fun TriviaEntity.toModel() = Trivia (
    id = this.id,
    poiId = this.poiId,
    pregunta = this.pregunta,
    opciones = listOf(this.opA, this.opB, this.opC, this.opD),
    respuestaCorrecta = this.respuestaCorrecta,
    respondida = this.respondida
)

fun Trivia.toEntity() = TriviaEntity(
    id = this.id,
    poiId = this.poiId,
    pregunta = this.pregunta,
    opA = this.opciones.getOrNull(0) ?: "",
    opB = this.opciones.getOrNull(1) ?: "",
    opC = this.opciones.getOrNull(2) ?: "",
    opD = this.opciones.getOrNull(3) ?: "",
    respuestaCorrecta = this.respuestaCorrecta,
    respondida = this.respondida
)