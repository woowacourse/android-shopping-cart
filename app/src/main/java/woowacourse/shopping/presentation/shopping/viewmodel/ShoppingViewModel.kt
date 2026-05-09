package woowacourse.shopping.presentation.shopping.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel
import woowacourse.shopping.presentation.shopping.model.ShoppingItemUiModel
import woowacourse.shopping.presentation.shopping.model.ShoppingUiState

class ShoppingViewModel(
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
    private val recentProductRepository: RecentProductRepository = RepositoryProvider.recentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private val pageSize = 20

    suspend fun initialize() {
        if (uiState.value.offset == 0) loadMore()
        loadRecentProducts(10)
    }

    suspend fun loadCartItemQuantities() {
        val quantities = cartRepository.getAllQuantities()
        _uiState.update {
            it.copy(
                products =
                    it.products.map { product ->
                        ShoppingItemUiModel(product.product, quantities[product.product.id] ?: 0)
                    },
                totalQuantity = quantities.values.sum(),
            )
        }
    }

    suspend fun increase(id: Long) {
        cartRepository.addItem(id)
        loadCartItemQuantities()
    }

    suspend fun decrease(id: Long) {
        cartRepository.decrease(id)
        loadCartItemQuantities()
    }

    suspend fun loadMore() {
        if (uiState.value.isLoading || !uiState.value.canLoadMore) return
        _uiState.update {
            it.copy(isLoading = true)
        }

        try {
            val loadData =
                getProductData(
                    offset = uiState.value.offset,
                    limit = pageSize,
                )
            val newItems = loadData.map { ShoppingItemUiModel(it, quantity = 0) }
            _uiState.update {
                it.copy(
                    products = it.products.plus(newItems),
                    offset = it.offset + loadData.size,
                    canLoadMore = loadData.size == pageSize,
                )
            }
            loadCartItemQuantities()
        } catch (e: Exception) {
            throw e
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    suspend fun upsertRecentProduct(id: Long) {
        recentProductRepository.upsertRecentProduct(id)
    }

    suspend fun loadRecentProducts(limit: Int) {
        _uiState.update {
            it.copy(
                recentProducts =
                    recentProductRepository.getRecentProducts(limit).map { product ->
                        product.toUiModel()
                    },
            )
        }
    }

    private suspend fun getProductData(
        offset: Int,
        limit: Int,
    ): ImmutableList<ProductUiModel> =
        productRepository
            .getProducts(offset, limit)
            .map { it.toUiModel() }
            .toImmutableList()
}
