package woowacourse.shopping.presentation.detail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel

class DetailStateHolder(
    private val scope: CoroutineScope,
    private val id: String,
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
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
