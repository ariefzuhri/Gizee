package com.ariefzuhri.gizee.core.database.util

import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import com.ariefzuhri.gizee.core.database.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.database.data.source.remote.response.NutrientResponse

fun mapResponsesToEntities(input: List<NutrientResponse>): List<NutrientEntity> {
    return input.map {
        NutrientEntity(
            id = it.attributeId!!,
            name = it.usdaNutrientDesc ?: "",
            unit = it.unit ?: ""
        )
    }
}

fun mapEntitiesToDomain(input: List<NutrientEntity>): List<Nutrient> {
    return input.map {
        Nutrient(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
    }
}

fun mapDomainToEntities(input: List<Nutrient>): List<NutrientEntity> {
    return input.map {
        NutrientEntity(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
    }
}