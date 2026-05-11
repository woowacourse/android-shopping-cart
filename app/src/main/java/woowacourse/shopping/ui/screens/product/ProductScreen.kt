package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(modifier = Modifier.padding(innerPadding)) {
            if (!uiState.isNetworkAvailable) {
                NetworkUnavailableBanner()
            }
            ProductScreenContent(
                uiState = uiState,
                innerPadding = PaddingValues(),
                onItemClick = onItemClick,
                onMinusClick = viewModel::minusAmount,
                onAddClick = viewModel::addAmount,
                onMoreClick = viewModel::getMoreProducts,
            )
        }
    }
}

@Composable
private fun NetworkUnavailableBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7D9C4))
            .padding(vertical = 6.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "네트워크에 연결되어 있지 않습니다",
            color = Color(0xFFFF0000),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ProductScreenContent(
    uiState: ProductUiState,
    innerPadding: PaddingValues,
    onItemClick: (String) -> Unit,
    onMinusClick: (String) -> Unit,
    onAddClick: (String) -> Unit,
    onMoreClick: () -> Unit,
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier
            .padding(innerPadding),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (uiState.showRecentProducts) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    RecentProducts(
                        items = uiState.recentProducts,
                        onClickItem = { onItemClick(it) },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    HorizontalDivider(thickness = 7.dp)
                }
            }
        }
        items(
            items = uiState.products,
            key = { it.id },
        ) {
            ProductCard(
                product = it,
                onClickItem = { onItemClick(it.id) },
                modifier = Modifier,
                onClickMinus = { onMinusClick(it.id) },
                onClickAdd = { onAddClick(it.id) },
            )
        }

        if (uiState.hasNext) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMoreClick()
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

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductScreenContent(
        uiState = ProductUiState(),
        innerPadding = PaddingValues(),
        onItemClick = {},
        onMinusClick = {},
        onAddClick = {},
        onMoreClick = {},
    )
}
