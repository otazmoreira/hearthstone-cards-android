package dev.tavieto.hearthstone.core.uikit.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> LazyColumnPaging(
    items: List<T>,
    requestNewPage: () -> Unit,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    itemCount: Int = 10,
    itemsPerPage: Int = 30
) {
    var lastIndexViewed by remember { mutableStateOf(0) }
    var itemVisibleCount by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = modifier,
        state = state,
        content = {
            this.items(items, itemContent = itemContent)
        }
    )

    LaunchedEffect(remember { derivedStateOf { state.layoutInfo } }.value.visibleItemsInfo.lastOrNull()?.index) {
        val last = state.layoutInfo.visibleItemsInfo.lastOrNull()
        if (last?.index != null && last.index > lastIndexViewed) {
            lastIndexViewed = last.index
        }
    }

    LaunchedEffect(remember { derivedStateOf { state.layoutInfo } }.value.visibleItemsInfo.size) {
        itemVisibleCount = state.layoutInfo.visibleItemsInfo.size
    }

    LaunchedEffect(Unit) {
        requestNewPage()
    }

    LaunchedEffect(lastIndexViewed) {
        if (state.layoutInfo.totalItemsCount > itemsPerPage) {
            val limit = (state.layoutInfo.totalItemsCount - itemCount)
            if (lastIndexViewed >= limit) {
                requestNewPage()
            }
        }
    }
}
