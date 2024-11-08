package com.chettri.feed_compose.ui.components.feed

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.runtime.Composable

interface FeedScope {
    fun item(
        key: Any? = null,
        span: (@ExtensionFunctionType LazyGridItemSpanScope.() -> GridItemSpan)? = null,
        contentType: Any? = null,
        content: @Composable LazyGridItemScope.() -> Unit,
    )

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        span: (@ExtensionFunctionType LazyGridItemSpanScope.(index: Int) -> GridItemSpan)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable LazyGridItemScope.(index: Int) -> Unit,
    )

}