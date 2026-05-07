package woowacourse.shopping.presentation.shopping.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel
import woowacourse.shopping.presentation.shopping.model.ShoppingUiState

class ShoppingViewModel(
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private val pageSize = 20

    suspend fun initialize() {
        if (uiState.value.offset == 0) loadMore()
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
            _uiState.update {
                it.copy(
                    products = it.products.plus(loadData),
                    offset = it.offset + loadData.size,
                    canLoadMore = loadData.size == pageSize,
                )
            }
        } catch (e: Exception) {
            throw e
        } finally {
            _uiState.update { it.copy(isLoading = false) }
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
