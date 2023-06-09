package com.andresestevez.soreh.ui.screens.characters.search

import androidx.sqlite.db.SimpleSQLiteQuery
import timber.log.Timber

class CharactersQueryBuilder(private val filter: CharactersFilter, private val count: Boolean = true) {

    fun build(): SimpleSQLiteQuery {
        val query = StringBuilder(if(count) "select count(id) from character where" else "select * from character where")

        addFilterName(query, filter.name)

        addFilter(query, "gender", filter.genders)
        addFilter(query, "alignment", filter.alignments)
        addFilter(query, "publisher", filter.publishers)
        addFilter(query, "race", filter.races)

        addFilterRange(query, "intelligence", filter.intelligence)
        addFilterRange(query, "strength", filter.strength)
        addFilterRange(query, "speed", filter.speed)
        addFilterRange(query, "durability", filter.durability)
        addFilterRange(query, "power", filter.power)
        addFilterRange(query, "combat", filter.combat)

        query.append(" order by ${filter.sort.first.value} ${filter.sort.second.value}")

        Timber.d(query.toString())

        return SimpleSQLiteQuery(query.toString())
    }


    private fun addFilterName(query: StringBuilder, filterValue: String) {
        if (filterValue.isNotBlank()) {
            query.append(
                if (query
                        .split(" ")
                        .last() == "where"
                ) {
                    " (name like '%$filterValue%' or fullName like '%$filterValue%')"
                } else {
                    " and (name like '%$filterValue%' or fullName like '%$filterValue%')"
                }
            )
        }
    }

    private fun addFilterRange(
        query: StringBuilder,
        field: String,
        filterValues: ClosedRange<Int>,
    ) {
        if (query
                .split(" ")
                .last() == "where"
        ) {
            query.append(" (${filterValues.start} <= $field and $field <= ${filterValues.endInclusive})")
        } else {
            query.append(" and (${filterValues.start} <= $field and $field <= ${filterValues.endInclusive})")
        }
    }

    private fun addFilter(
        query: StringBuilder,
        field: String,
        filterValues: List<CharacterFieldValues>,
    ) {
        if (filterValues.isNotEmpty()) {
            filterValues.map {
                it.value
            }.joinTo(
                buffer = if (query.split(" ").last() == "where") query
                else query.append(" and "),
                separator = "\",\"",
                prefix = " $field in (\"",
                postfix = "\")"
            )
        }
    }

}