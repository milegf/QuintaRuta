package com.example.quintaruta.data.seed

import com.example.quintaruta.data.local.AppDatabase
import com.example.quintaruta.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataSeeder {

    fun seedAll(database: AppDatabase) {

        CoroutineScope(Dispatchers.IO).launch {
            val userDao = database.userDao()
            val rutaDao = database.rutaDao()
            val poiDao = database.poiDao()
            val triviaDao = database.triviaDao()

            // USUARIO DE PRUEBA
            if (userDao.getAllUsers().isEmpty()) {
                val usuarioPrueba = UserEntity(
                    id = 1L,
                    email = "admin@quintaruta.cl",
                    password = "1234"
                )
                userDao.insertUser(usuarioPrueba)
            }

            // RUTAS BASE
            if (rutaDao.getAllRutasList().isEmpty()) {
                val rutaMiradores = RutaEntity(
                    id = 1L,
                    nombre = "Ruta Miradores",
                    descripcion = "Recorre los principales miradores de Valparaíso y Viña del Mar.",
                    categoria = "Cultural",
                    imagenUrl = "ruta_miradores.png",
                    creador = "admin@quintaruta.cl"
                )

                val rutaCosta = RutaEntity(
                    id = 2L,
                    nombre = "Ruta Circuito Costa",
                    descripcion = "Explora las playas y puntos emblemáticos del borde costero de Viña del Mar.",
                    categoria = "Turística",
                    imagenUrl = "ruta_costa.png",
                    creador = "admin@quintaruta.cl"
                )

                rutaDao.insertAll(listOf(rutaMiradores, rutaCosta))
            }

            // POIs
            if (poiDao.getAllPois().isEmpty()) {

                val poisMiradores = listOf(
                    PoiEntity(
                        id = 1L,
                        rutaId = 1L,
                        nombre = "Mirador 21 de Mayo",
                        descripcion = "Famoso por su vista panorámica del puerto de Valparaíso.",
                        latitud = -33.0381,
                        longitud = -71.6297,
                        imagenUrl = "badge_mirador_21_mayo.png"
                    ),
                    PoiEntity(
                        id = 2L,
                        rutaId = 1L,
                        nombre = "Mirador Esperanza",
                        descripcion = "Ubicado en Cerro Alegre, rodeado de coloridos murales y arte urbano.",
                        latitud = -33.0412,
                        longitud = -71.6279,
                        imagenUrl = "badge_mirador_esperanza.png"
                    ),
                    PoiEntity(
                        id = 3L,
                        rutaId = 1L,
                        nombre = "Mirador Pablo Neruda",
                        descripcion = "En Camino Cintura, ofrece una vista del anfiteatro natural de Valparaíso.",
                        latitud = -33.0408,
                        longitud = -71.6199,
                        imagenUrl = "badge_mirador_neruda.png"
                    ),
                    PoiEntity(
                        id = 4L,
                        rutaId = 1L,
                        nombre = "Mirador Portales",
                        descripcion = "Mirador con vista al mar y la costa, ideal para ver atardeceres.",
                        latitud = -33.0325,
                        longitud = -71.6368,
                        imagenUrl = "badge_mirador_portales.png"
                    ),
                    PoiEntity(
                        id = 5L,
                        rutaId = 1L,
                        nombre = "Mirador Reñaca",
                        descripcion = "Mirador con vista panorámica a las playas de Viña del Mar.",
                        latitud = -32.9674,
                        longitud = -71.5512,
                        imagenUrl = "badge_mirador_renaca.png"
                    )
                )

                val poisCosta = listOf(
                    PoiEntity(
                        id = 6L,
                        rutaId = 2L,
                        nombre = "Playa Acapulco y Los Marineros",
                        descripcion = "Tradicional playa de Viña del Mar, ideal para descansar y disfrutar del sol.",
                        latitud = -33.0173,
                        longitud = -71.5552,
                        imagenUrl = "badge_costa_acapulco_marineros.png"
                    ),
                    PoiEntity(
                        id = 7L,
                        rutaId = 2L,
                        nombre = "Playa Caleta Abarca",
                        descripcion = "Famosa por su cercanía al Reloj de Flores y el Castillo Wulff.",
                        latitud = -33.0235,
                        longitud = -71.5507,
                        imagenUrl = "badge_costa_caleta_abarca.png"
                    ),
                    PoiEntity(
                        id = 8L,
                        rutaId = 2L,
                        nombre = "Mirador de Cochoa",
                        descripcion = "Mirador con vista hacia Reñaca y Concón, perfecto para observar aves marinas.",
                        latitud = -32.9537,
                        longitud = -71.5274,
                        imagenUrl = "badge_costa_cochoa.png"
                    ),
                    PoiEntity(
                        id = 9L,
                        rutaId = 2L,
                        nombre = "Muelle Vergara",
                        descripcion = "Histórico muelle reconvertido en paseo peatonal con vistas al mar.",
                        latitud = -33.0102,
                        longitud = -71.5517,
                        imagenUrl = "badge_costa_muelle_vergara.png"
                    ),
                    PoiEntity(
                        id = 10L,
                        rutaId = 2L,
                        nombre = "Reloj de Flores",
                        descripcion = "Símbolo icónico de Viña del Mar, con su diseño floral y mecánico.",
                        latitud = -33.0275,
                        longitud = -71.5510,
                        imagenUrl = "badge_costa_reloj_flores.png"
                    )
                )

                poiDao.insertAll(poisMiradores + poisCosta)
            }

            // TRIVIAS
            if (triviaDao.getAllTriviasList().isEmpty()) {
                val trivias = mutableListOf<TriviaEntity>()

                // RUTA MIRADORES
                trivias += listOf(
                    // Mirador 21 de Mayo
                    TriviaEntity(
                        poiId = 1L,
                        pregunta = "¿Qué se puede observar desde el Mirador 21 de Mayo?",
                        opA = "El puerto y la bahía",
                        opB = "Los cerros interiores",
                        opC = "El centro de Santiago",
                        opD = "La cordillera",
                        respuestaCorrecta = "El puerto y la bahía"
                    ),
                    TriviaEntity(
                        poiId = 1L,
                        pregunta = "¿Dónde se ubica el Mirador 21 de Mayo?",
                        opA = "Cerro Alegre",
                        opB = "Cerro Artillería",
                        opC = "Cerro Concepción",
                        opD = "Camino Cintura",
                        respuestaCorrecta = "Cerro Artillería"
                    ),
                    TriviaEntity(
                        poiId = 1L,
                        pregunta = "¿Qué museo se encuentra cerca del Mirador 21 de Mayo?",
                        opA = "Museo Naval y Marítimo",
                        opB = "Museo de Bellas Artes",
                        opC = "Museo a Cielo Abierto",
                        opD = "Museo La Sebastiana",
                        respuestaCorrecta = "Museo Naval y Marítimo"
                    ),

                    // Mirador Esperanza
                    TriviaEntity(
                        poiId = 2L,
                        pregunta = "¿Qué caracteriza al Mirador Esperanza?",
                        opA = "Sus murales coloridos",
                        opB = "Un faro antiguo",
                        opC = "Un museo arqueológico",
                        opD = "Una feria artesanal",
                        respuestaCorrecta = "Sus murales coloridos"
                    ),
                    TriviaEntity(
                        poiId = 2L,
                        pregunta = "¿En qué cerro se encuentra el Mirador Esperanza?",
                        opA = "Cerro Alegre",
                        opB = "Cerro Polanco",
                        opC = "Cerro Barón",
                        opD = "Cerro Bellavista",
                        respuestaCorrecta = "Cerro Alegre"
                    ),
                    TriviaEntity(
                        poiId = 2L,
                        pregunta = "¿Qué tipo de arte predomina alrededor del Mirador Esperanza?",
                        opA = "Arte urbano y murales",
                        opB = "Esculturas metálicas",
                        opC = "Fotografía callejera",
                        opD = "Grafitis antiguos",
                        respuestaCorrecta = "Arte urbano y murales"
                    ),

                    // Mirador Pablo Neruda
                    TriviaEntity(
                        poiId = 3L,
                        pregunta = "¿Qué poeta chileno inspira el nombre del Mirador Pablo Neruda?",
                        opA = "Pablo Neruda",
                        opB = "Gabriela Mistral",
                        opC = "Vicente Huidobro",
                        opD = "Nicanor Parra",
                        respuestaCorrecta = "Pablo Neruda"
                    ),
                    TriviaEntity(
                        poiId = 3L,
                        pregunta = "¿Desde qué punto del mirador se puede ver la casa La Sebastiana?",
                        opA = "Desde su terraza principal",
                        opB = "Desde el acceso inferior",
                        opC = "Desde la zona de estacionamientos",
                        opD = "No se puede ver, está en otro cerro",
                        respuestaCorrecta = "Desde su terraza principal"
                    ),
                    TriviaEntity(
                        poiId = 3L,
                        pregunta = "¿Qué panorama suele disfrutarse desde el Mirador Pablo Neruda?",
                        opA = "Vista del anfiteatro de Valparaíso y la bahía",
                        opB = "Vista del reloj de flores",
                        opC = "Vista del Muelle Vergara",
                        opD = "Vista del Cerro Santa Lucía",
                        respuestaCorrecta = "Vista del anfiteatro de Valparaíso y la bahía"
                    ),

                    // Mirador Portales
                    TriviaEntity(
                        poiId = 4L,
                        pregunta = "¿Por qué es conocido el Mirador Portales?",
                        opA = "Por su vista directa al mar y los atardeceres",
                        opB = "Por sus ferias gastronómicas",
                        opC = "Por un antiguo faro restaurado",
                        opD = "Por estar dentro de un museo marítimo",
                        respuestaCorrecta = "Por su vista directa al mar y los atardeceres"
                    ),
                    TriviaEntity(
                        poiId = 4L,
                        pregunta = "¿Qué universidad se encuentra cerca del Mirador Portales?",
                        opA = "Universidad Técnica Federico Santa María",
                        opB = "Universidad de Valparaíso",
                        opC = "Pontificia Universidad Católica de Valparaíso",
                        opD = "Universidad Andrés Bello",
                        respuestaCorrecta = "Universidad Técnica Federico Santa María"
                    ),
                    TriviaEntity(
                        poiId = 4L,
                        pregunta = "¿Qué elemento marino es posible observar frecuentemente desde este mirador?",
                        opA = "Barcos y aves costeras",
                        opB = "Lobos marinos",
                        opC = "Pesqueros artesanales",
                        opD = "Ballenas azules",
                        respuestaCorrecta = "Barcos y aves costeras"
                    ),

                    // Mirador Reñaca
                    TriviaEntity(
                        poiId = 5L,
                        pregunta = "¿Dónde se encuentra el Mirador Reñaca?",
                        opA = "En la parte alta del sector Reñaca, Viña del Mar",
                        opB = "En el centro de Valparaíso",
                        opC = "En Cerro Polanco",
                        opD = "En Concón",
                        respuestaCorrecta = "En la parte alta del sector Reñaca, Viña del Mar"
                    ),
                    TriviaEntity(
                        poiId = 5L,
                        pregunta = "¿Qué vista ofrece el Mirador Reñaca?",
                        opA = "Vista panorámica de las playas y edificios costeros",
                        opB = "Vista del Parque Quinta Vergara",
                        opC = "Vista del Cerro Alegre",
                        opD = "Vista de la Cordillera de los Andes",
                        respuestaCorrecta = "Vista panorámica de las playas y edificios costeros"
                    ),
                    TriviaEntity(
                        poiId = 5L,
                        pregunta = "¿Qué actividad es popular entre los visitantes del Mirador Reñaca?",
                        opA = "Tomar fotografías del atardecer",
                        opB = "Practicar escalada",
                        opC = "Asistir a conciertos al aire libre",
                        opD = "Comprar artesanías locales",
                        respuestaCorrecta = "Tomar fotografías del atardecer"
                    )
                )

                // RUTA CIRCUITO COSTA
                trivias += listOf(
                    // Playa Acapulco y Los Marineros
                    TriviaEntity(
                        poiId = 6L,
                        pregunta = "¿Qué destaca en la Playa Acapulco de Viña del Mar?",
                        opA = "Sus arenas doradas y ambiente familiar",
                        opB = "Un muelle antiguo",
                        opC = "Un faro costero",
                        opD = "Un paseo de esculturas",
                        respuestaCorrecta = "Sus arenas doradas y ambiente familiar"
                    ),
                    TriviaEntity(
                        poiId = 6L,
                        pregunta = "¿Qué nombre tiene la playa vecina de Acapulco?",
                        opA = "Playa Caleta Abarca",
                        opB = "Playa del Sol",
                        opC = "Playa Los Marineros",
                        opD = "Playa Blanca",
                        respuestaCorrecta = "Playa Los Marineros"
                    ),
                    TriviaEntity(
                        poiId = 6L,
                        pregunta = "¿Qué actividad es común en Playa Acapulco?",
                        opA = "Voleibol y deportes de arena",
                        opB = "Pesca artesanal",
                        opC = "Surf profesional",
                        opD = "Paseos en bote",
                        respuestaCorrecta = "Voleibol y deportes de arena"
                    ),

                    // Playa Caleta Abarca
                    TriviaEntity(
                        poiId = 7L,
                        pregunta = "¿Qué atractivo turístico se encuentra cerca de Caleta Abarca?",
                        opA = "El Reloj de Flores",
                        opB = "La Quinta Vergara",
                        opC = "El Castillo Brunet",
                        opD = "El Parque Sausalito",
                        respuestaCorrecta = "El Reloj de Flores"
                    ),
                    TriviaEntity(
                        poiId = 7L,
                        pregunta = "¿Cómo es el oleaje en Caleta Abarca?",
                        opA = "Tranquilo, ideal para nadar",
                        opB = "Moderado con algunas corrientes",
                        opC = "Fuerte y peligroso",
                        opD = "Casi inexistente",
                        respuestaCorrecta = "Moderado con algunas corrientes"
                    ),
                    TriviaEntity(
                        poiId = 7L,
                        pregunta = "¿Qué monumento está a un costado de la playa?",
                        opA = "Castillo Wulff",
                        opB = "Museo Fonck",
                        opC = "Anfiteatro Quinta Vergara",
                        opD = "Mirador de Cochoa",
                        respuestaCorrecta = "Castillo Wulff"
                    ),

                    // Mirador de Cochoa
                    TriviaEntity(
                        poiId = 8L,
                        pregunta = "¿Qué se puede observar desde el Mirador de Cochoa?",
                        opA = "Aves marinas y lobos de mar",
                        opB = "El Reloj de Flores",
                        opC = "El Estadio Sausalito",
                        opD = "El centro de Viña del Mar",
                        respuestaCorrecta = "Aves marinas y lobos de mar"
                    ),
                    TriviaEntity(
                        poiId = 8L,
                        pregunta = "¿Entre qué dos sectores se ubica el Mirador de Cochoa?",
                        opA = "Reñaca y Concón",
                        opB = "Caleta Abarca y Muelle Vergara",
                        opC = "Viña Centro y Recreo",
                        opD = "Playa Blanca y Sausalito",
                        respuestaCorrecta = "Reñaca y Concón"
                    ),
                    TriviaEntity(
                        poiId = 8L,
                        pregunta = "¿Por qué es popular el Mirador de Cochoa?",
                        opA = "Por su vista panorámica al mar y fauna costera",
                        opB = "Por sus restaurantes típicos",
                        opC = "Por su museo de arte moderno",
                        opD = "Por sus ferias de artesanía",
                        respuestaCorrecta = "Por su vista panorámica al mar y fauna costera"
                    ),

                    // Muelle Vergara
                    TriviaEntity(
                        poiId = 9L,
                        pregunta = "¿Qué era originalmente el Muelle Vergara?",
                        opA = "Un muelle industrial para carga y descarga",
                        opB = "Un embarcadero de pesca artesanal",
                        opC = "Un muelle turístico desde su construcción",
                        opD = "Una plataforma militar",
                        respuestaCorrecta = "Un muelle industrial para carga y descarga"
                    ),
                    TriviaEntity(
                        poiId = 9L,
                        pregunta = "¿Qué puedes hacer actualmente en el Muelle Vergara?",
                        opA = "Caminar y disfrutar la vista al mar",
                        opB = "Tomar un ferry a Valparaíso",
                        opC = "Visitar un museo subterráneo",
                        opD = "Practicar buceo recreativo",
                        respuestaCorrecta = "Caminar y disfrutar la vista al mar"
                    ),
                    TriviaEntity(
                        poiId = 9L,
                        pregunta = "¿Qué estructura visible acompaña al muelle desde la costa?",
                        opA = "La Grúa Vergara restaurada",
                        opB = "El Reloj de Flores",
                        opC = "El Faro Punta Ángeles",
                        opD = "El puente Libertad",
                        respuestaCorrecta = "La Grúa Vergara restaurada"
                    ),

                    // Reloj de Flores
                    TriviaEntity(
                        poiId = 10L,
                        pregunta = "¿Qué hace único al Reloj de Flores de Viña del Mar?",
                        opA = "Su mecanismo real y su carátula de flores naturales",
                        opB = "Su tamaño de 20 metros de altura",
                        opC = "Ser el más antiguo de Chile",
                        opD = "Estar hecho completamente de mármol",
                        respuestaCorrecta = "Su mecanismo real y su carátula de flores naturales"
                    ),
                    TriviaEntity(
                        poiId = 10L,
                        pregunta = "¿Con qué motivo fue creado el Reloj de Flores?",
                        opA = "Con motivo del Mundial de Fútbol de 1962",
                        opB = "Por el aniversario de Viña del Mar",
                        opC = "Para conmemorar el centenario de Chile",
                        opD = "Por la inauguración del Parque Quinta Vergara",
                        respuestaCorrecta = "Con motivo del Mundial de Fútbol de 1962"
                    ),
                    TriviaEntity(
                        poiId = 10L,
                        pregunta = "¿Dónde se encuentra el Reloj de Flores?",
                        opA = "Al pie del Cerro Castillo",
                        opB = "En la entrada de la Quinta Vergara",
                        opC = "En el borde costero de Reñaca",
                        opD = "En el Cerro Barón",
                        respuestaCorrecta = "Al pie del Cerro Castillo"
                    )
                )

                triviaDao.insertAll(trivias)
            }

        }
    }

}
