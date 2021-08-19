package com.ariefzuhri.gizee.core.data.repository.datamapper

import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.domain.model.History

internal fun mapEntitiesToDomain(input: List<HistoryEntity>): List<History> {
    return input.map {
        History(
            query = it.query,
            timestamp = it.timestamp
        )
    }
}

internal fun mapEntityToDomain(inputHistory: HistoryEntity, inputFoods: List<FoodEntity>): History {
    return History(
        query = inputHistory.query,
        timestamp = inputHistory.timestamp,
        foods = mapEntitiesToDomain(inputFoods)
    )
}

internal fun mapDomainToEntity(input: History): HistoryEntity {
    return HistoryEntity(
        query = input.query,
        timestamp = input.timestamp
    )
}