package com.example.abc_app.domain.usecase

import com.example.abc_app.R
import com.example.abc_app.domain.model.ListItem

class GetListItemsUseCase {

    private val _listDataMap = listOf(
        listOf(
            ListItem("Apple", "Subtitle of Apple", R.drawable.apple),
            ListItem("Avocado", "Subtitle of Avocado", R.drawable.avocado),
            ListItem("Apricot", "Subtitle of Apricot", R.drawable.apricot)
        ),
        listOf(
            ListItem("Banana", "Subtitle of Banana", R.drawable.banana),
            ListItem("Blueberry", "Subtitle of Blueberry", R.drawable.blueberry),
            ListItem("Blackberry", "Subtitle of Blackberry", R.drawable.blackberry),
            ListItem("Beach Plum", "Subtitle of Beach Plum", R.drawable.beachplum)
        ),
        listOf(
            ListItem("Cherry", "Subtitle of Cherry", R.drawable.cherry),
            ListItem("Coconut", "Subtitle of Coconut", R.drawable.coconut),
            ListItem("Cranberry", "Subtitle of Cranberry", R.drawable.cranberry),
            ListItem("Carrot", "Subtitle of Carrot", R.drawable.carrot),
            ListItem("Cantaloupe", "Subtitle of Cantaloupe", R.drawable.cantaloupe)
        )
    )

    fun getByPosition(position: Int): List<ListItem> = _listDataMap[position % _listDataMap.size]

    fun filter(list: List<ListItem>, query: String): List<ListItem> =
        if (query.isEmpty()) list else list.filter {
            it.title.contains(query, ignoreCase = true)
        }

    fun top3Characters(list: List<ListItem>): List<Pair<Char, Int>> {
        val charCount = mutableMapOf<Char, Int>()
        list.forEach { (label, _) ->
            label.toLowerCase().filter { ch -> ch.isLetter() }
                .forEach { ch -> charCount[ch] = charCount.getOrDefault(ch, 0) + 1 }
        }
        return charCount.entries.sortedByDescending { it.value }.take(3).map { it.toPair() }
    }


}