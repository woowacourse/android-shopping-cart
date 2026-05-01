package woowacourse.shopping.feature.detail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.core.repository.CartRepository
import woowacourse.shopping.core.repository.InMemoryProductRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel
import woowacourse.shopping.feature.cart.model.AddItemResult

class DetailStateHolder(
    private val scope: CoroutineScope,
    private val id: String,
    private val productRepository: ProductRepository = InMemoryProductRepository(),
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

    fun addToCart(onResult: (AddItemResult) -> Unit) {
        scope.launch {
            val product = productRepository.getProductById(id)
            val result = cartRepository.addItem(product)
            onResult(result)
        }
    }
}
