package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import javax.inject.Inject

class GetRandomCharactersListUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke(maxItems: Int = 10): Result<List<Character>> = repository.getRandomCharactersList(maxItems)
}