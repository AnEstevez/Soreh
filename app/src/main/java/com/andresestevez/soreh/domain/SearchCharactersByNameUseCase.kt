package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.data.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharactersByNameUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(name: String): Flow<List<Character>> = repository.searchCharactersByName(name)

}