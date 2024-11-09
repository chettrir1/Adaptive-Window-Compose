package com.chettri.feed_compose.ui.screen.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chettri.feed_compose.data.Sweets
import com.chettri.feed_compose.ui.components.CustomTopAppBar

@Composable
fun SweetDetailScreen(
    sweets: Sweets,
    windowSizeClass: WindowSizeClass,
    onBackPressed: () -> Unit,
) {
    val scrollState = rememberScrollState()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> SweetDetailHorizontal(
            sweets = sweets,
            scrollState = scrollState,
            onBackPressed = onBackPressed,
        )

        WindowWidthSizeClass.Compact -> SweetDetailVertical(
            sweets = sweets,
            scrollState = scrollState,
            onBackPressed = onBackPressed
        )

        else ->
            when (windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Expanded -> SweetDetailVertical(
                    sweets = sweets,
                    scrollState = scrollState,
                    onBackPressed = onBackPressed
                )

                else -> SweetDetailHorizontal(
                    sweets = sweets,
                    scrollState = scrollState,
                    onBackPressed = onBackPressed
                )
            }

    }
}

@Composable
fun SweetDetailHorizontal(
    sweets: Sweets,
    scrollState: ScrollState,
    onBackPressed: () -> Unit,
) {
    Column {
        CustomTopAppBar(onBackPressed = onBackPressed)
        Row {
            AsyncImage(
                model = sweets.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.0f)
            )
            Text(
                text = stringResource(sweets.description),
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 32.dp)
                    .weight(1.0f)
                    .verticalScroll(scrollState)
            )
        }
    }

}

@Composable
fun SweetDetailVertical(
    sweets: Sweets,
    scrollState: ScrollState,
    onBackPressed: () -> Unit,
) {
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        CustomTopAppBar(onBackPressed = onBackPressed)
        AsyncImage(
            model = sweets.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1.414f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = sweets.description),
            modifier = Modifier.padding(32.dp)
        )
    }
}

