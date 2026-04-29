package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import woowacourse.shopping.ui.state.ProductUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: () -> Unit,
    onCartIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    productUiModels: List<ProductUiModel>,
) {
    Scaffold(
        containerColor = Color.White,
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductListAppBar(onCartIconClick = onCartIconClick)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            ProductList(
                products = productUiModels,
                onProductClick = onProductClick,
            )
        }
    }
}
