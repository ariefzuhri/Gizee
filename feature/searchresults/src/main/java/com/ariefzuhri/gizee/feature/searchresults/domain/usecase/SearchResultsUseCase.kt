package com.ariefzuhri.gizee.feature.searchresults.domain.usecase

import com.ariefzuhri.gizee.core.common.dto.Resource
import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

interface SearchResultsUseCase {

    fun getSearchResults(query: String): Flow<Resource<History>>
}