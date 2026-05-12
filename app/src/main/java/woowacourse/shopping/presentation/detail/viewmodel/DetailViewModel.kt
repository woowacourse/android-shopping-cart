package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiEvents = Channel<DetailEvent>(Channel.BUFFERED)
    val uiEvents: Flow<DetailEvent> = _uiEvents.receiveAsFlow()

    private var loadedProduct: Product? = null

    fun loadProduct(
        id: Long,
        isFromLastSeen: Boolean,
    ) {
        if (_uiState.value !is DetailUiState.Loading) return

        viewModelScope.launch {
            try {
                val loaded = productRepository.getProductById(id)
                loadedProduct = loaded

                val lastSeen =
                    runCatching {
                        if (!isFromLastSeen) {
                            recentProductRepository
                                .getRecentProducts(limit = 1)
                                .firstOrNull()
                                ?.toUiModel()
                        } else {
                            null
                        }
                    }.getOrNull()

                _uiState.value =
                    DetailUiState.Success(
                        product = loaded.toUiModel(),
                        quantity = 1,
                        lastSeenProduct = lastSeen,
                    )
                if (!isFromLastSeen) {
                    runCatching {
                        recentProductRepository.upsertRecentProduct(id)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error("상품 로딩에 실패했습니다.")
                _uiEvents.send(DetailEvent.ShowErrorToast("상품 로딩에 실패했습니다."))
            }
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
            _uiEvents.send(DetailEvent.NavigateToCart)
        }
    }
}

sealed interface DetailEvent {
    data class ShowErrorToast(
        val message: String,
    ) : DetailEvent

    data object NavigateToCart : DetailEvent
}
