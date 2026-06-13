package org.amrubio27.rickmortyapp.ui.core.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

enum class PagingType {
    ROW,
    COLUMN,
    VERTICAL_GRID
}

@Composable
fun <T : Any> PagingWrapper(
    pagingType: PagingType,
    pagingItems: LazyPagingItems<T>,
    initialView: @Composable () -> Unit = {},
    emptyView: @Composable () -> Unit = {},
    extraItemsView: @Composable () -> Unit = {},
    itemView: @Composable (T) -> Unit
) {
    when {
        pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0 -> {
            // carga inicial
            initialView()
        }

        pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 -> {
            emptyView()
        }

        else -> {
            when (pagingType) {
                PagingType.ROW -> {
                    LazyRow {
                        items(pagingItems.itemCount) { pos ->
                            pagingItems[pos]?.let { item ->
                                itemView(item)
                            }
                        }
                    }
                }

                PagingType.COLUMN -> {
                    LazyColumn {
                        items(pagingItems.itemCount) { pos ->
                            pagingItems[pos]?.let { item ->
                                itemView(item)
                            }
                        }
                    }
                }

                PagingType.VERTICAL_GRID -> {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(pagingItems.itemCount) { pos ->
                            pagingItems[pos]?.let { item ->
                                itemView(item)
                            }
                        }
                    }
                }
            }

            if (pagingItems.loadState.append is LoadState.Loading) {
                extraItemsView()
            }
        }
    }
}