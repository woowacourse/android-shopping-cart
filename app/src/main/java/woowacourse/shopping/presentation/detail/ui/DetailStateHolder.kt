package woowacourse.shopping.presentation.detail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel

class DetailStateHolder(
    private val id: String,
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
) {
    private var loadedProduct: Product? = null

    var product by mutableStateOf(
        ProductUiModel(
            id = "",
            name = "",
            imageUrl = "",
            price = 0,
        ),
    )

    suspend fun loadProduct() {
        val loaded = productRepository.getProductById(id)
        loadedProduct = loaded
        product = loaded.toUiModel()
    }

    suspend fun addToCart(): AddItemResult {
        val target =
            loadedProduct ?: productRepository.getProductById(id).also {
                loadedProduct = it
            }
        return cartRepository.addItem(target)
    }
}
