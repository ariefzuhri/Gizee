package com.ariefzuhri.gizee.core.data.repository.datamapper

import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.domain.model.Nutrient

internal fun mapResponsesToEntities(input: List<NutrientResponse>): List<NutrientEntity> {
    return input.map {
        NutrientEntity(
            id = it.attrId!!,
            name = it.usdaNutrDesc ?: "",
            unit = it.unit ?: ""
        )
    }
}

internal fun mapEntitiesToDomain(input: List<NutrientEntity>): List<Nutrient> {
    return input.map {
        Nutrient(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
    }
}

internal fun mapDomainToEntities(input: List<Nutrient>): List<NutrientEntity> {
    return input.map {
        NutrientEntity(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
    }
}