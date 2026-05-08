package woowacourse.shopping.ui.productdetail

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.productdetail.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailRoute(
    productDetailViewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModel.Factory),
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by productDetailViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isError) {
        if (uiState.isError) {
            Toast.makeText(context, "상품 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            onNavigateToHome()
        }
    }

    uiState.product?.let { product ->
        ProductDetailScreen(
            imageUrl = product.imageUrl,
            title = product.title,
            price = product.price,
            quantity = uiState.selectedQuantity,
            onIncrement = { productDetailViewModel.increment() },
            onDecrement = { productDetailViewModel.decrement() },
            onCloseClick = onNavigateToHome,
            onAddToCartClick = onNavigateToHome,
        )
    }
}
