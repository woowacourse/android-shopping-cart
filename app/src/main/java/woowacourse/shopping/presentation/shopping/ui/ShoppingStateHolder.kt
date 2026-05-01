package woowacourse.shopping.presentation.shopping.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.InMemoryProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel

class ShoppingStateHolder(
    private val scope: CoroutineScope,
    private val productRepository: ProductRepository = InMemoryProductRepository(),
    private val getCurrentProducts: () -> List<ProductUiModel>,
    private val onProductsChanged: (List<ProductUiModel>) -> Unit,
) {
    var canLoadMore by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    private var offset = 0
    private val pageSize = 20

    init {
        offset = getCurrentProducts().size
        if (offset == 0) {
            loadMore()
        }
    }

    fun loadMore() {
        if (isLoading || !canLoadMore) return
        isLoading = true
        scope.launch {
            try {
                val loadData = getProductData(offset, pageSize)
                val currentProducts = getCurrentProducts().plus(loadData)
                onProductsChanged(currentProducts)
                offset += loadData.size
                canLoadMore = loadData.size == pageSize
            } catch (e: Exception) {
                throw e
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun getProductData(
        offset: Int,
        limit: Int,
    ): ImmutableList<ProductUiModel> = productRepository.getProducts(offset, limit).map { it.toUiModel() }.toImmutableList()
}
