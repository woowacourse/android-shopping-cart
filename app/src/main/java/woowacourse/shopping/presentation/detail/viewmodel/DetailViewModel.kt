package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.di.RepositoryProvider.cartRepository
import woowacourse.shopping.di.RepositoryProvider.productRepository
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.toUiModel
import woowacourse.shopping.presentation.detail.model.DetailUiState

class DetailViewModel(
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var loadedProduct: Product? = null

    suspend fun loadProduct(id: String) {
        val loaded = productRepository.getProductById(id)
        loadedProduct = loaded
        _uiState.update {
            it.copy(
                product = loaded.toUiModel(),
                quantity = cartRepository.getQuantity(loaded.id),
            )
        }
    }

    fun increase() {
        _uiState.update {
            it.copy(
                quantity = it.quantity + 1,
            )
        }
    }

    fun decrease() {
        _uiState.update {
            it.copy(
                quantity = it.quantity - 1,
            )
        }
    }

    suspend fun addToCart(
        id: String,
        quantity: Int,
    ): AddItemResult {
        val product =
            loadedProduct ?: productRepository.getProductById(id).also {
                loadedProduct = it
            }
        return cartRepository.addItem(product.id, quantity)
    }
}
