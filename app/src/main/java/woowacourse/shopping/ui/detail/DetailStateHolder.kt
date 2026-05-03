package woowacourse.shopping.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.MockProductRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.ui.model.ProductUiModel
import woowacourse.shopping.ui.model.toUiModel

class DetailStateHolder(
    private val scope: CoroutineScope,
    private val id: String,
    private val onProductNotFound: () -> Unit,
    private val productRepository: ProductRepository = MockProductRepository(),
    private val cartRepository: CartRepository = CartRepository,
) {
    var product by mutableStateOf(
        ProductUiModel(),
    )

    init {
        loadProduct()
    }

    fun loadProduct() {
        scope.launch {
            try {
                product = productRepository.getProductById(id).toUiModel()
            } catch (e: IllegalArgumentException) {
                onProductNotFound()
            }
        }
    }

    fun addToCart(
        onSuccess: () -> Unit,
        onAlreadyExists: () -> Unit,
    ) {
        scope.launch {
            try {
                val product = productRepository.getProductById(id)
                val addedResult = cartRepository.addItem(product)
                if (addedResult) {
                    onSuccess()
                } else {
                    onAlreadyExists()
                }
            } catch (e: IllegalArgumentException) {
                onProductNotFound()
            }
        }
    }
}
