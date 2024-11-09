package com.chettri.feed_compose.ui.screen.sweet

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chettri.feed_compose.R
import com.chettri.feed_compose.data.DataProvider
import com.chettri.feed_compose.data.Sweets
import com.chettri.feed_compose.ui.components.feed.FeedComponent
import com.chettri.feed_compose.ui.components.feed.FeedScopeHelper.action
import com.chettri.feed_compose.ui.components.feed.FeedScopeHelper.footer
import com.chettri.feed_compose.ui.components.feed.FeedScopeHelper.items
import com.chettri.feed_compose.ui.components.feed.FeedScopeHelper.row
import com.chettri.feed_compose.ui.components.feed.FeedScopeHelper.title
import com.chettri.feed_compose.ui.components.feed.Filter
import kotlinx.coroutines.launch

@Composable
internal fun SweetsScreen(
    windowSizeClass: WindowSizeClass,
    onSweetSelected: (Sweets) -> Unit = {},
) {
    val selectedFilter: MutableState<Filter> = remember {
        mutableStateOf(Filter.All)
    }
    val lazyGridState = rememberLazyGridState()
    val sweets = DataProvider.sweets.filter { selectedFilter.value.apply(it) }
    val chocolates = DataProvider.chocolates
    val coroutineScope = rememberCoroutineScope()

    FeedComponent(
        columns = rememberColumns(windowSizeClass = windowSizeClass),
        state = lazyGridState,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        title(contentType = "feed-title") {
            FeedTitle(text = stringResource(R.string.app_name))
        }

        items(DataProvider.misc, contentType = { "sweets" }, key = { it.id }) {
            SquareSweetsCard(sweets = it, onClick = onSweetSelected)
        }

        title(contentType = "section-title") {
            SectionTitle(text = stringResource(R.string.chocolate))
        }

        row(contentType = "chocolate-list") {
            HorizontalSweetsList(
                sweets = chocolates,
                cardWidth = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                    240.dp
                } else {
                    128.dp
                },
                onSweetSelected = onSweetSelected
            )
        }

        title(contentType = "section-title") {
            SectionTitle(text = stringResource(id = R.string.candy_or_pastry))
        }

        action(
            contentType = "filter-selector",
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterSelector(selectedFilter = selectedFilter.value) { selectedFilter.value = it }
        }

        items(sweets, contentType = { "sweets" }, key = { it.id }) {
            SquareSweetsCard(sweets = it, onClick = onSweetSelected)
        }

        footer {
            BackToTopButton(modifier = Modifier.padding(PaddingValues(top = 32.dp))) {
                coroutineScope.launch {
                    lazyGridState.animateScrollToItem(0)
                }
            }
        }
    }
}

@Composable
fun rememberColumns(windowSizeClass: WindowSizeClass) = remember(windowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> GridCells.Fixed(1)
        WindowWidthSizeClass.Medium -> GridCells.Fixed(2)
        else -> GridCells.Adaptive(240.dp)
    }
}

@Composable
fun FeedTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(PaddingValues(vertical = 24.dp))
    )
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(PaddingValues(top = 32.dp, bottom = 8.dp))
    )

}

@Composable
fun SquareSweetsCard(
    sweets: Sweets,
    modifier: Modifier = Modifier,
    onClick: (Sweets) -> Unit = {},
) {
    SweetsCard(
        sweets,
        modifier = modifier.aspectRatio(1.0f),
        onClick = onClick
    )
}

@Composable
fun HorizontalSweetsList(
    sweets: List<Sweets>,
    cardWidth: Dp,
    onSweetSelected: (Sweets) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(PaddingValues(bottom = 16.dp)),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sweets.size, key = { sweets[it].id }, contentType = { "sweets" }) {
            PortraitSweetsCard(
                sweets = sweets[it],
                modifier = Modifier.width(cardWidth),
                onClick = onSweetSelected
            )
        }
    }
}

@Composable
fun FilterSelector(selectedFilter: Filter, onFilterSelected: (Filter) -> Unit) {
    val filters = listOf(
        Filter.All to R.string.all,
        Filter.Candy to R.string.candy,
        Filter.Pastry to R.string.pastry
    )

    filters.forEach { (filter, labelId) ->
        val selected = selectedFilter == filter
        FilterChip(
            selected = selected,
            onClick = { onFilterSelected(filter) },
            label = { Text(text = stringResource(id = labelId)) },
            leadingIcon = {
                if (selected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
fun BackToTopButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(onClick = onClick, modifier = modifier) {
            Icon(painter = painterResource(R.drawable.ic_arrow_upward), null)
            Text(text = stringResource(R.string.back_to_top))
        }
    }
}

@Composable
fun PortraitSweetsCard(
    sweets: Sweets,
    modifier: Modifier = Modifier,
    onClick: (Sweets) -> Unit,
) {
    SweetsCard(
        sweets,
        modifier = modifier.aspectRatio(0.707f),
        onClick = onClick
    )

}

@Composable
fun SweetsCard(
    sweets: Sweets,
    modifier: Modifier = Modifier,
    onClick: (Sweets) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val outlineColor = if (isFocused) {
        MaterialTheme.colorScheme.outline
    } else {
        MaterialTheme.colorScheme.background
    }
    val context = LocalContext.current
    Card(
        modifier = modifier
            .onFocusChanged { isFocused = it.isFocused }
            .border(width = 2.dp, color = outlineColor),
        onClick = {
            onClick(sweets)
            Toast.makeText(context, sweets.imageUrl, Toast.LENGTH_SHORT).show()
        }) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = sweets.imageUrl,
            contentDescription = stringResource(R.string.app_name),
            placeholder = painterResource(R.drawable.placeholder_sweets),
            contentScale = ContentScale.Crop
        )
    }

}