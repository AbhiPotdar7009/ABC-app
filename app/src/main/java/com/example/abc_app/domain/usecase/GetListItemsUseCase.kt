package com.example.abc_app.domain.usecase

import android.content.Context
import com.example.abc_app.R
import com.example.abc_app.domain.model.ListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetListItemsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _listDataMap = listOf(
        listOf(
            ListItem(
                context.getString(R.string.title_apple),
                context.getString(R.string.subtitle_apple),
                R.drawable.apple
            ),
            ListItem(
                context.getString(R.string.title_avocado),
                context.getString(R.string.subtitle_avocado),
                R.drawable.avocado
            ),
            ListItem(
                context.getString(R.string.title_apricot),
                context.getString(R.string.subtitle_apricot),
                R.drawable.apricot
            )
        ),
        listOf(
            ListItem(
                context.getString(R.string.title_banana),
                context.getString(R.string.subtitle_banana),
                R.drawable.banana
            ),
            ListItem(
                context.getString(R.string.title_blueberry),
                context.getString(R.string.subtitle_blueberry),
                R.drawable.blueberry
            ),
            ListItem(
                context.getString(R.string.title_blackberry),
                context.getString(R.string.subtitle_blackberry),
                R.drawable.blackberry
            ),
            ListItem(
                context.getString(R.string.title_beachplum),
                context.getString(R.string.subtitle_beachplum),
                R.drawable.beachplum
            )
        ),
        listOf(
            ListItem(
                context.getString(R.string.title_cherry),
                context.getString(R.string.subtitle_cherry),
                R.drawable.cherry
            ),
            ListItem(
                context.getString(R.string.title_coconut),
                context.getString(R.string.subtitle_coconut),
                R.drawable.coconut
            ),
            ListItem(
                context.getString(R.string.title_cranberry),
                context.getString(R.string.subtitle_cranberry),
                R.drawable.cranberry
            ),
            ListItem(
                context.getString(R.string.title_carrot),
                context.getString(R.string.subtitle_carrot),
                R.drawable.carrot
            ),
            ListItem(
                context.getString(R.string.title_cantaloupe),
                context.getString(R.string.subtitle_cantaloupe),
                R.drawable.cantaloupe
            )
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