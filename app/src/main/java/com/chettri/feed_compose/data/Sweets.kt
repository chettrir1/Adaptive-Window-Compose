package com.chettri.feed_compose.data

import androidx.annotation.StringRes
import com.chettri.feed_compose.R

class Sweets {
    val id: Int = 0
    val imgUrl: String = ""

    @StringRes
    val description: Int = 0
    val category: Category = Category.Misc
}

enum class Category(@StringRes val labelId: Int) {
    Pastry(R.string.pastry),
    Candy(R.string.candy),
    Chocolate(R.string.chocolate),
    Misc(R.string.misc)
}