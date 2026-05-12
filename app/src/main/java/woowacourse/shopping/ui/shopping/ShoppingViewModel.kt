package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.network.NetworkObserver
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.CartResult
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import woowacourse.shopping.model.Cart
import woowacourse.shopping.ui.model.mapper.toUiModel
import java.io.IOException

class ShoppingViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentItemRepository: RecentItemRepository,
    private val networkObserver: NetworkObserver,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var offset = 0
    private val pageSize = 20

    init {
        observeNetwork()
        observeCart()
        observeRecentItems()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkObserver.observeNetwork().collect { isAvailable ->
                _uiState.value =
                    _uiState.value.copy(isNetworkAvailable = isAvailable)

                if (isAvailable && _uiState.value.products.isEmpty()) {
                    loadMore()
                    loadRecentItems()
                }
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCartItems().collect { entities ->
                val result =
                    runCatching {
                        Cart(
                            items =
                                entities.map { entity ->
                                    val product = productRepository.getProductById(entity.id)
                                    entity.toDomain(product)
                                },
                        )
                    }.fold(
                        onSuccess = { CartResult.Success(it) },
                        onFailure = { CartResult.Failure(it) },
                    )

                when (result) {
                    is CartResult.Success -> {
                        val cart = result.cart
                        _uiState.value =
                            _uiState.value.copy(
                                cartSize = cart.getTotalQuantity(),
                                cartQuantities = cart.items.associate { it.product.id to it.quantity },
                                cartErrorMessage = null,
                            )
                    }

                    is CartResult.Failure -> {
                        _uiState.value =
                            _uiState.value.copy(
                                cartErrorMessage = "장바구니 상품 정보를 불러오지 못했습니다.",
                            )
                    }
                }
            }
        }
    }

    private fun observeRecentItems() {
        viewModelScope.launch {
            recentItemRepository.getRecentItems().collect { recentItems ->
                _uiState.value =
                    _uiState.value.copy(
                        recentItems = recentItems.map { it.toUiModel() }.toImmutableList(),
                    )
            }
        }
    }

    fun loadRecentItems() {
        viewModelScope.launch {
            val recentItems =
                recentItemRepository.getRecentItems().first().map { it.toUiModel() }.toImmutableList()

            _uiState.value = _uiState.value.copy(recentItems = recentItems)
        }
    }

    fun loadMore() {
        if (!_uiState.value.isNetworkAvailable || !_uiState.value.canLoadMore || _uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val loadProducts =
                    productRepository.getProducts(offset, pageSize).map { it.toUiModel() }

                offset += loadProducts.size

                _uiState.value =
                    _uiState.value.copy(
                        products = (_uiState.value.products + loadProducts).toImmutableList(),
                        canLoadMore = loadProducts.size == pageSize,
                    )
            } catch (e: IOException) {
                _uiState.value = _uiState.value
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun updateQuantity(
        productId: String,
        quantity: Int,
    ) {
        viewModelScope.launch {
            if (productId !in _uiState.value.cartQuantities) {
                val product = productRepository.getProductById(productId)
                cartRepository.setQuantity(product.id, quantity = quantity)
            } else {
                cartRepository.updateQuantity(productId, quantity = quantity)
            }
        }
    }

    companion object {
        fun provideFactory(
            productRepository: ProductRepository,
            cartRepository: CartRepository,
            recentItemRepository: RecentItemRepository,
            networkObserver: NetworkObserver,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ShoppingViewModel(
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                        recentItemRepository = recentItemRepository,
                        networkObserver = networkObserver,
                    )
                }
            }
    }
}
