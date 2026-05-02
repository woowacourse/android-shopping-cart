package woowacourse.shopping.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.MockRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.ui.model.ProductUiModel
import woowacourse.shopping.ui.model.toUiModel

class DetailStateHolder(
    private val scope: CoroutineScope,
    private val id: String,
    private val productRepository: ProductRepository = MockRepository(),
    private val cartRepository: CartRepository = CartRepository,
) {
    var product by mutableStateOf(
        ProductUiModel(
            id = "",
            name = "",
            imageUrl = "",
            price = 0,
        ),
    )

    init {
        loadProduct()
    }

    fun loadProduct() {
        scope.launch {
            product = productRepository.getProductById(id).toUiModel()
        }
    }

    fun addToCart(onResult: (Boolean) -> Unit) {
        scope.launch {
            val product = productRepository.getProductById(id)
            onResult(cartRepository.addItem(product))
        }
    }
}
