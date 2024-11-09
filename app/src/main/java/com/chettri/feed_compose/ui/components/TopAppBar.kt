package com.chettri.feed_compose.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.chettri.feed_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomTopAppBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        navigationIcon = { BackButton(onBackPressed = onBackPressed) }
    )

}

@Composable
private fun BackButton(onBackPressed: () -> Unit) {
    IconButton(onClick = onBackPressed) {
        Icon(painter = painterResource(R.drawable.ic_arrow_back), contentDescription = null)
    }
}