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
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
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
            _uiState.value =
                DetailUiState.Success(
                    product = loaded.toUiModel(),
                    quantity = 1,
                    lastSeenProduct = lastSeen,
                )

            if (!isFromLastSeen) recentProductRepository.upsertRecentProduct(id)
        }
    }

    fun increase() {
        _uiState.update { state ->
            if (state is DetailUiState.Success) {
                state.copy(quantity = state.quantity + 1)
            } else {
                state
            }
        }
    }

    fun decrease() {
        _uiState.update { state ->
            if (state is DetailUiState.Success && state.quantity > 1) {
                state.copy(quantity = state.quantity - 1)
            } else {
                state
            }
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
