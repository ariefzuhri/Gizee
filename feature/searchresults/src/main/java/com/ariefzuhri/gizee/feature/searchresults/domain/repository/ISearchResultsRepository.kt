package com.ariefzuhri.gizee.feature.searchresults.domain.repository

import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

interface ISearchResultsRepository {

    fun getSearchResults(query: String): Flow<Resource<History>>
}