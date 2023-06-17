package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import javax.inject.Inject

class GetCharactersByIdList @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(idList: List<Int>, idListOrder: String) = repository.getCharactersByIdList(idList, idListOrder)

}