package com.chettri.feed_compose.ui.components.feed

import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

object FeedScopeHelper {
    /**
     * Adds a list of items to the feed with optional properties for key, span, and contentType.
     */
    inline fun <T> FeedScope.items(
        items: List<T>,
        noinline key: ((item: T) -> Any)? = null,
        noinline span: (@ExtensionFunctionType LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
        noinline contentType: (item: T) -> Any? = { null },
        crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
    ) = items(
        count = items.size,
        key = if (key != null) {
            { index -> key(items[index]) }
        } else null,
        span = if (span != null) {
            { index -> span(items[index]) }
        } else null,
        contentType = { index -> contentType(items[index]) }
    ) { index -> itemContent(items[index]) }

    /**
     * Adds indexed items with optional key, span, and contentType.
     */
    inline fun <T> FeedScope.itemsIndexed(
        items: List<T>,
        noinline key: ((index: Int, item: T) -> Any)? = null,
        noinline span: (@ExtensionFunctionType LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
        noinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
        crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit,
    ) = items(
        count = items.size,
        key = if (key != null) {
            { index -> key(index, items[index]) }
        } else null,
        span = if (span != null) {
            { index -> span(index, items[index]) }
        } else null,
        contentType = { index -> contentType(index, items[index]) }
    ) { index -> itemContent(index, items[index]) }

    /**
     * Creates a single-row item that spans the entire line.
     */
    inline fun FeedScope.row(
        key: Any? = null,
        contentType: Any? = null,
        crossinline content: @Composable LazyGridItemScope.() -> Unit,
    ) = item(
        key = key,
        contentType = contentType,
        span = { GridItemSpan(maxLineSpan) },
    ) {
        content()
    }

    /**
     * Creates a title row for the feed.
     */
    inline fun FeedScope.title(
        key: Any? = null,
        contentType: Any? = null,
        crossinline content: @Composable LazyGridItemScope.() -> Unit,
    ) = row(key = key, contentType = contentType) { content() }

    /**
     * Creates an action row with horizontal scrolling capability.
     */
    inline fun FeedScope.action(
        key: Any? = null,
        contentType: Any? = null,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        crossinline content: @Composable RowScope.() -> Unit,
    ) = row(
        key = key,
        contentType = contentType,
    ) {
        Row(
            modifier = Modifier
                .focusGroup()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = horizontalArrangement
        ) {
            content()
        }
    }

    /**
     * Adds a footer item that spans the entire line.
     */
    inline fun FeedScope.footer(
        key: Any? = null,
        contentType: Any? = null,
        crossinline content: @Composable LazyGridItemScope.() -> Unit,
    ) = item(
        key = key,
        span = { GridItemSpan(maxLineSpan) },
        contentType = contentType
    ) {
        content()
    }
}