package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<Result<List<Character>>> = repository.getFavorites()

}