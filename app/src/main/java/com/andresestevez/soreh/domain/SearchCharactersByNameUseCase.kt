package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.data.CharactersRepository
import kotlinx.coroutines.flow.Flow

class SearchCharactersByNameUseCase(private val repository: CharactersRepository) {

    operator fun invoke(name: String): Flow<List<Character>> = repository.searchCharactersByName(name)

}