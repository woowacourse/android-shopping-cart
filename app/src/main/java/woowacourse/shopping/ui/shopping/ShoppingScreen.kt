package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.ui.common.component.ShoppingLoading
import woowacourse.shopping.ui.shopping.component.ProductGroup
import woowacourse.shopping.ui.shopping.component.RecentProductGroup
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onRecentProductClick: (Product) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val isConnected by viewModel.isNetworkConnected.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.syncCartState()
                }
            }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (!isConnected) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "인터넷 연결이 끊겼습니다. 오프라인 모드입니다. 😥")
        }
    } else {
        Box(modifier = modifier) {
            ShoppingScreen(
                products = state.visibleProducts,
                recentProducts = state.recentProducts,
                cartCount = state.cartCount,
                hasNext = state.hasNext,
                lazyGridState = lazyGridState,
                onCartClick = onCartClick,
                onProductClick = onProductClick,
                onMoreClick = { viewModel.loadMore() },
                onIncreaseClick = { viewModel.increase(it) },
                onDecreaseClick = { viewModel.decrease(it) },
                onRecentProductClick = onRecentProductClick,
            )

            if (state.isLoading) ShoppingLoading()
        }
    }
}

@Composable
fun ShoppingScreen(
    products: List<ProductUiModel>,
    recentProducts: Products,
    cartCount: Int,
    hasNext: Boolean,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
    onIncreaseClick: (Product) -> Unit,
    onDecreaseClick: (Product) -> Unit,
    onRecentProductClick: (Product) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShoppingHeader(
            cartCount = cartCount,
            onCartClick = onCartClick,
        )

        if (recentProducts.any()) {
            RecentProductGroup(
                products = recentProducts,
                modifier = Modifier.fillMaxWidth(),
                onRecentProductClick = onRecentProductClick,
            )

            HorizontalDivider(thickness = 7.dp, color = Color(0xFFEBEBEB))
        }

        ProductGroup(
            products = products,
            showMoreButton = hasNext,
            lazyGridState = lazyGridState,
            modifier =
                Modifier
                    .padding(20.dp)
                    .weight(1f),
            onProductClick = onProductClick,
            onMoreClick = onMoreClick,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick,
        )
    }
}

@Preview(showBackground = true, name = "상품 여러개")
@Composable
private fun ShoppingScreenPreview1() {
    val product1 =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    val product2 =
        Product(
            name = "연금복권",
            price = Money(1000),
            imageUrl = "",
        )
    val product3 =
        Product(
            name = "로또",
            price = Money(1000),
            imageUrl = "",
        )
    val productUiModels = listOf(product1, product2, product3).map { ProductUiModel(it) }

    ShoppingScreen(
        products = productUiModels,
        recentProducts = Products(listOf(product1)),
        cartCount = 1,
        hasNext = true,
        lazyGridState = rememberLazyGridState(),
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
        modifier = Modifier,
        onRecentProductClick = {},
    )
}

@Preview(showBackground = true, name = "상품 0개")
@Composable
private fun ShoppingScreenPreview2() {
    ShoppingScreen(
        products = emptyList(),
        recentProducts = Products(emptyList()),
        cartCount = 0,
        hasNext = false,
        lazyGridState = rememberLazyGridState(),
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
        modifier = Modifier,
        onRecentProductClick = {},
    )
}
