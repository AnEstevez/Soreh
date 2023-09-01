package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomCharactersListUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(maxItems: Int = 10): Flow<Result<List<Character>>> = repository.getRandomCharactersList(maxItems)
}