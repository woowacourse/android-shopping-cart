package woowacourse.shopping.presentation.shopping.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.detail.DetailActivity
import woowacourse.shopping.presentation.shopping.model.ShoppingItemUiModel
import woowacourse.shopping.presentation.shopping.viewmodel.ShoppingViewModel

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    CartIcon(
                        quantity = state.totalQuantity,
                        onNavigateToCart = onNavigateToCart,
                    )
                },
                modifier = modifier.fillMaxWidth(),
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isLoading) CircularProgressIndicator()
            ShoppingContents(
                items = state.products.toImmutableList(),
                onLoad = {
                    scope.launch {
                        viewModel.loadMore()
                    }
                },
                isCanLoadMore = state.canLoadMore,
                onProductCardClick = {
                    activity?.startActivity(DetailActivity.newIntent(activity, it))
                },
                onIncrease = { id ->
                    scope.launch { viewModel.increase(id) }
                },
                onDecrease = { id ->
                    scope.launch { viewModel.decrease(id) }
                },
                onUpsertRecentProduct = { id ->
                    scope.launch { viewModel.upsertRecentProduct(id) }
                },
                recentProducts = state.recentProducts.toImmutableList(),
            )
        }
    }
}

@Composable
private fun ShoppingContents(
    items: ImmutableList<ShoppingItemUiModel>,
    onLoad: () -> Unit,
    onProductCardClick: (String) -> Unit,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onUpsertRecentProduct: (String) -> Unit,
    recentProducts: ImmutableList<ProductUiModel>,
    isCanLoadMore: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 20.dp),
        ) {
            item(
                span = { GridItemSpan(2) },
            ) {
                RecentSection(
                    recentProducts = recentProducts,
                )
            }
            items(
                items = items,
                key = { it.product.id },
            ) { item ->
                ProductCard(
                    product = item.product,
                    quantity = item.quantity,
                    onClick = {
                        onProductCardClick(item.product.id)
                        onUpsertRecentProduct(item.product.id)
                    },
                    onIncrease = { onIncrease(item.product.id) },
                    onDecrease = { onDecrease(item.product.id) },
                )
            }
            if (isCanLoadMore) {
                item(
                    span = { GridItemSpan(2) },
                ) {
                    LoadButton(
                        onClick = onLoad,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        viewModel = viewModel(),
        onNavigateToCart = {},
    )
}
