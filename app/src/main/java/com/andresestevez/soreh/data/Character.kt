package com.andresestevez.soreh.data

data class Character(
    val id: Int,
    val title: String,
    val thumb: String,
    val type: Type
) {
    enum class Type {PHOTO, VIDEO}
}

fun getCharacters() = (11..18).map {
    Character(
        id = it,
        title = "Title $it",
        thumb = "https://www.superherodb.com/pictures2/portraits/10/100/$it.jpg",
        type = if (it % 3 == 0 ) Character.Type.VIDEO else Character.Type.PHOTO
    )
}