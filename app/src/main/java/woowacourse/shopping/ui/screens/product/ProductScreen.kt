package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.ui.component.topbar.MainTopBar

@Composable
fun ProductScreen(
    onIconClick: () -> Unit,
    onItemClick: (String) -> Unit,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory),
) {
    val uiState: ProductUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    Scaffold(
        topBar = {
            MainTopBar(
                title = "Shopping",
                onIconClick = onIconClick,
                count = uiState.totalCartAmount,
                modifier = Modifier.statusBarsPadding(),
            )
        },
        modifier = Modifier.statusBarsPadding(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .padding(innerPadding),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = uiState.products,
                key = { it.id },
            ) {
                ProductCard(
                    product = it,
                    onClickItem = { onItemClick(it.id) },
                    modifier = Modifier,
                    onClickMinus = { viewModel.minusAmount(it.id) },
                    onClickAdd = { viewModel.addAmount(it.id) },
                )
            }

            if (uiState.hasNext) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.getMoreProducts()
                            },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = "상품 더보기",
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                                .align(Alignment.Center),
                            tint = Color(0xFF555555),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductScreenPreview() {
    ProductScreen(
        onIconClick = { },
        onItemClick = { },
    )
}
