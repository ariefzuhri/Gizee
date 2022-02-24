package com.ariefzuhri.gizee.core.database.util

import com.ariefzuhri.gizee.core.database.domain.model.History
import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.HistoryEntity

fun mapEntitiesToDomain(input: List<HistoryEntity>): List<History> {
    return input.map {
        History(
            query = it.query,
            timestamp = it.timestamp
        )
    }
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