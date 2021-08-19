package com.ariefzuhri.gizee.core.utils.datamapper

import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.domain.model.Nutrient

fun mapResponsesToEntities(input: List<NutrientResponse>): List<NutrientEntity> {
    val result = arrayListOf<NutrientEntity>()
    input.forEach {
        val nutrient = NutrientEntity(
            id = it.attrId!!,
            name = it.usdaNutrDesc ?: "",
            unit = it.unit ?: ""
        )
        result.add(nutrient)
    }
    return result
}

fun mapEntitiesToDomain(input: List<NutrientEntity>): List<Nutrient> {
    val result = arrayListOf<Nutrient>()
    input.forEach {
        val nutrient = Nutrient(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
        result.add(nutrient)
    }
    return result
}

fun mapDomainToEntities(input: List<Nutrient>): List<NutrientEntity> {
    val result = arrayListOf<NutrientEntity>()
    input.forEach {
        val nutrientEntity = NutrientEntity(
            id = it.id,
            name = it.name,
            unit = it.unit,
            value = it.value
        )
        result.add(nutrientEntity)
    }
    return result
}