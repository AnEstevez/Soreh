package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(id: Int): Flow<Result<Character>> = repository.getCharacterById(id)
}