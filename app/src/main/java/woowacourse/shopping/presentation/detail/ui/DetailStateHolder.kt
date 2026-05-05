package woowacourse.shopping.presentation.detail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.di.RepositoryProvider.cartRepository
import woowacourse.shopping.di.RepositoryProvider.productRepository
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel

class DetailStateHolder(
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

    suspend fun loadProduct() {
        product = productRepository.getProductById(id).toUiModel()
    }

    suspend fun addToCart(): AddItemResult {
        val product = productRepository.getProductById(id)
        return cartRepository.addItem(product)
    }
}
