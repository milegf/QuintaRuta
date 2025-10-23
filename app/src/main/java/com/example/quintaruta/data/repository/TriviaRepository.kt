package com.example.quintaruta.data.repository

import com.example.quintaruta.data.local.dao.TriviaDao
import com.example.quintaruta.data.local.entity.TriviaEntity
import com.example.quintaruta.util.toModel
import com.example.quintaruta.util.toEntity
import com.example.quintaruta.data.model.Trivia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TriviaRepository(
    private val triviaDao: TriviaDao
) {

    fun getAllTrivias(): Flow<List<Trivia>> =
        triviaDao.getAllTrivias().map { list -> list.map { it.toModel() } }

    fun getTriviasByPoi(poiId: Long): Flow<List<Trivia>> =
        triviaDao.getTriviasByPoi(poiId).map { it.map(TriviaEntity::toModel) }

    suspend fun insertTrivia(trivia: Trivia) {
        try {
            triviaDao.insertTrivia(trivia.toEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insertAll(trivias: List<Trivia>) {
        try {
            triviaDao.insertAll(trivias.map { it.toEntity() })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateTrivia(trivia: Trivia) {
        try {
            triviaDao.updateTrivia(trivia.toEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteTrivia(trivia: Trivia) {
        try {
            triviaDao.deleteTrivia(trivia.toEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteAll() {
        try {
            triviaDao.deleteAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

