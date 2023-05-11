package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.data.Repository
import kotlinx.coroutines.flow.Flow

class SearchCharactersByNameUseCase(private val repository: Repository) {

    operator fun invoke(name: String): Flow<List<Character>> = repository.searchCharactersByName(name)

}