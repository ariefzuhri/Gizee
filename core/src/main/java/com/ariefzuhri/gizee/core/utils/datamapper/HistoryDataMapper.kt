package com.ariefzuhri.gizee.core.utils.datamapper

import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.domain.model.History

fun mapEntitiesToDomain(input: List<HistoryEntity>): List<History> {
    val result = arrayListOf<History>()
    input.forEach {
        val history = History(
            query = it.query,
            timestamp = it.timestamp
        )
        result.add(history)
    }
    return result
}

fun mapEntityToDomain(inputHistory: HistoryEntity, inputFoods: List<FoodEntity>): History {
    return History(
        query = inputHistory.query,
        timestamp = inputHistory.timestamp,
        foods = mapEntitiesToDomain(inputFoods)
    )
}

fun mapDomainToEntity(input: History): HistoryEntity {
    return HistoryEntity(
        query = input.query,
        timestamp = input.timestamp
    )
}