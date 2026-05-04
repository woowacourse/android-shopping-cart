package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.ui.state.ProductUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (ProductUiModel) -> Unit,
    onCartIconClick: () -> Unit,
    onLoading: () -> Unit,
    modifier: Modifier = Modifier,
    productUiModels: List<ProductUiModel>,
    isEnd: Boolean,
    isLoading: Boolean,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductListAppBar(onCartIconClick = onCartIconClick)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                } else {
                    ProductList(
                        products = productUiModels,
                        onProductClick = onProductClick,
                        onLoading = onLoading,
                        isEnd = isEnd,
                    )
                }
            }
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
        productUiModels = emptyList(),
        isEnd = false,
        isLoading = false,
    )
}
