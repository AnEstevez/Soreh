package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke(character: Character) =
        repository.updateCharacter(character.copy(bookmarked = !character.bookmarked))
}