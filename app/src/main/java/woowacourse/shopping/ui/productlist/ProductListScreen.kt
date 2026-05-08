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
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.SimpleProductUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productUiModels: List<DetailProductUiModel>,
    recentProductUiModels: List<SimpleProductUiModel>,
    cartCount: Int,
    isEnd: Boolean,
    onProductClick: (String) -> Unit,
    onIncrement: (String) -> Unit,
    onDecrement: (String) -> Unit,
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
                count = cartCount,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            RecentProductList(
                recentProducts = recentProductUiModels,
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
            DetailProductUiModel(
                title = "상품1",
                price = "1000원",
                imageUrl = "",
                id = "1",
                quantity = 1,
            ),
            DetailProductUiModel(
                title = "상품2",
                price = "1000원",
                imageUrl = "",
                id = "2",
                quantity = 1,
            ),
            DetailProductUiModel(
                title = "상품3",
                price = "1000원",
                imageUrl = "",
                id = "3",
                quantity = 1,
            ),
            DetailProductUiModel(
                title = "상품4",
                price = "1000원",
                imageUrl = "",
                id = "4",
                quantity = 0,
            ),
            DetailProductUiModel(
                title = "상품5",
                price = "1000원",
                imageUrl = "",
                id = "5",
                quantity = 0,
            ),
        ),
        isEnd = false,
        onIncrement = {},
        onDecrement = {},
        recentProductUiModels = listOf(
            SimpleProductUiModel(
                id = "1",
                imageUrl = "",
                title = "최근 본 상품1",
            ),
        ),
        cartCount = 3,
    )
}
