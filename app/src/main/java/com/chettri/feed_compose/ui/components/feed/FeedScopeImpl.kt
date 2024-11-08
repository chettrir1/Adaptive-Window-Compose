package com.chettri.feed_compose.ui.components.feed

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.runtime.Composable

/**
Data class that represents a single feed item
 */
internal data class FeedItem(
    val count: Int, // The number of items (usually 1, unless 'items' is used)
    val key: ((index: Int) -> Any)?, // Key function, used for item identity and optimizations
    val span: (LazyGridItemSpanScope.(index: Int) -> GridItemSpan)?, // Span function, determines how many columns the item should span
    val contentType: (index: Int) -> Any?, // Content type function, useful for type optimizations
    val itemContent: @Composable LazyGridItemScope.(index: Int) -> Unit, // The Composable content for the item, rendered using LazyGridItemScope
)

/**
A class that implements the FeedScope interface, which handles adding items to the feed
 */

internal class FeedScopeImpl : FeedScope {
    /**
    A mutable list to store FeedItem objects that will represent the feed items
     */
    val items = mutableListOf<FeedItem>()

    override fun item(
        key: Any?,
        span: (LazyGridItemSpanScope.() -> GridItemSpan)?,
        contentType: Any?,
        content: @Composable() (LazyGridItemScope.() -> Unit),
    ) {
        items.add(
            FeedItem(
                count = 1,//count is always 1 for single item
                key = if (key != null) {
                    { key }
                } else null,
                span = if (span != null) {
                    { span() }
                } else null,
                contentType = { contentType },
                itemContent = { content() },
            )
        )
    }

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        span: (LazyGridItemSpanScope.(index: Int) -> GridItemSpan)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable() (LazyGridItemScope.(index: Int) -> Unit),
    ) {
        items.add(
            FeedItem(
                count = count,
                key = key,
                span = span,
                contentType = contentType,
                itemContent = itemContent
            )
        )
    }

}
