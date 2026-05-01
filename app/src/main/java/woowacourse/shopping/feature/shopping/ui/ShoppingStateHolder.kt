package woowacourse.shopping.feature.shopping.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.core.repository.InMemoryProductRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class ShoppingStateHolder(
    private val scope: CoroutineScope,
    private val productRepository: ProductRepository = InMemoryProductRepository(),
) {
    var currentProducts by mutableStateOf(emptyList<ProductUiModel>())
    var canLoadMore by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    private var offset = 0
    private val pageSize = 20

    init {
        loadMore()
    }

    fun loadMore() {
        if (isLoading || !canLoadMore) return
        scope.launch {
            isLoading = true
            val loadData = getProductData(offset, pageSize)
            currentProducts = currentProducts.plus(loadData)
            offset += loadData.size
            canLoadMore = loadData.size == pageSize
            isLoading = false
        }
    }

    private suspend fun getProductData(
        offset: Int,
        limit: Int,
    ): ImmutableList<ProductUiModel> = productRepository.getProducts(offset, limit).map { it.toUiModel() }.toImmutableList()
}
