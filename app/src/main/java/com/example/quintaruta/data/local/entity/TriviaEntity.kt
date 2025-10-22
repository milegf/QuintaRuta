package com.example.quintaruta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "trivias",
    foreignKeys = [
        ForeignKey(
            entity = PoiEntity::class,
            parentColumns = ["id"],
            childColumns = ["poi_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class TriviaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "poi_id")
    val poiId: Long,

    val pregunta: String,

    val opA: String,
    val opB: String,
    val opC: String,
    val opD: String,

    @ColumnInfo(name = "respuesta_correcta")
    val respuestaCorrecta: String,

    val respondida: Boolean = false
)
