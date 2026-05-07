package woowacourse.shopping.ui.productdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

class ProductDetailScreenState(
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    private val coroutineScope: CoroutineScope,
) {
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var productToShow: Product? by mutableStateOf(null)
        private set

    fun addToCart(product: Product) {
        isLoading = true
        coroutineScope.launch {
            try {
                cartRepo.add(product)
            } finally {
                isLoading = false
            }
        }
    }

    fun findProduct(id: UUID) {
        isLoading = true
        coroutineScope.launch {
            try {
                productToShow = productRepo.findProduct(id)
            } finally {
                isLoading = false
            }
        }
    }
}

@Composable
fun rememberProductDetailScreenState(
    productRepo: ProductRepository,
    cartRepo: CartRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): ProductDetailScreenState =
    remember {
        ProductDetailScreenState(
            productRepo = productRepo,
            cartRepo = cartRepo,
            coroutineScope = coroutineScope,
        )
    }
