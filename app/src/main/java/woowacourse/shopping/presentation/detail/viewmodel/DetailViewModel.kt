package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.di.RepositoryProvider.cartRepository
import woowacourse.shopping.di.RepositoryProvider.productRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.common.model.toUiModel
import woowacourse.shopping.presentation.detail.model.DetailUiState

class DetailViewModel(
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
    private val recentProductRepository: RecentProductRepository = RepositoryProvider.recentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var loadedProduct: Product? = null

    fun loadProduct(
        id: Long,
        isFromLastSeen: Boolean,
    ) {
        viewModelScope.launch {
            val loaded = productRepository.getProductById(id)
            loadedProduct = loaded

            val lastSeen =
                if (!isFromLastSeen) {
                    recentProductRepository
                        .getRecentProducts(limit = 1)
                        .firstOrNull()
                        ?.toUiModel()
                } else {
                    null
                }

            _uiState.update {
                it.copy(
                    product = loaded.toUiModel(),
                    quantity = cartRepository.getQuantity(loaded.id),
                    lastSeenProduct = lastSeen,
                )
            }

            if (!isFromLastSeen) recentProductRepository.upsertRecentProduct(id)
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
        if (_uiState.value.quantity == 1) return
        _uiState.update {
            it.copy(
                quantity = it.quantity - 1,
            )
        }
    }

    fun addToCart(
        id: Long,
        quantity: Int,
    ) {
        viewModelScope.launch {
            val product =
                loadedProduct ?: productRepository.getProductById(id).also {
                    loadedProduct = it
                }
            cartRepository.addItem(product.id, quantity)
        }
    }
}
