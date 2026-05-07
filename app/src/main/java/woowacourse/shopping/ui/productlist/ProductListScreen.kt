package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.state.ProductUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productUiModels: List<ProductUiModel>,
    isEnd: Boolean,
    onProductClick: (String) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onCartIconClick: () -> Unit,
    onLoading: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductListAppBar(
                onCartIconClick = onCartIconClick,
                count = productUiModels.size,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            RecentProductList(
                recentProducts = productUiModels,
                modifier = Modifier.padding(20.dp),
            )

            HorizontalDivider(
                thickness = 7.dp,
                color = Color(0xFFEBEBEB),
            )
            ProductList(
                products = productUiModels,
                onProductClick = onProductClick,
                modifier = Modifier.weight(1f),
                onLoading = onLoading,
                isEnd = isEnd,
                onIncrement = onIncrement,
                onDecrement = onDecrement,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductListScreen() {
    ProductListScreen(
        onProductClick = { },
        onCartIconClick = { },
        onLoading = {},
        productUiModels = listOf(
            ProductUiModel(
                title = "상품1",
                price = "1000원",
                imageUrl = "",
                id = "1",
            ),
            ProductUiModel(
                title = "상품2",
                price = "1000원",
                imageUrl = "",
                id = "2",
            ),
            ProductUiModel(
                title = "상품3",
                price = "1000원",
                imageUrl = "",
                id = "3",
            ),
            ProductUiModel(
                title = "상품4",
                price = "1000원",
                imageUrl = "",
                id = "4",
            ),
            ProductUiModel(
                title = "상품5",
                price = "1000원",
                imageUrl = "",
                id = "5",
            ),
        ),
        isEnd = false,
        onIncrement = {},
        onDecrement = {},
    )
}
