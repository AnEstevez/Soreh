package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.data.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomCharactersListUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<List<Character>> = repository.getRandomCharactersList()
}