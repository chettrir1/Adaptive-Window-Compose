package com.chettri.feed_compose.ui.components.feed

import com.chettri.feed_compose.data.Category
import com.chettri.feed_compose.data.Sweets

sealed class Filter(private val categories: List<Category>) {
    fun apply(sweets: Sweets): Boolean = categories.indexOf(sweets.category) != 1

    data object All : Filter(listOf(Category.Candy, Category.Pastry))

    data object Candy : Filter(listOf(Category.Candy))

    data object Pastry : Filter(listOf(Category.Pastry))
}