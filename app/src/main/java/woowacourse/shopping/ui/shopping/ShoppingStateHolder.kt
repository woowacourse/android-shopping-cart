package woowacourse.shopping.ui.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.MockRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.ui.model.ProductUiModel
import woowacourse.shopping.ui.model.toUiModel

class ShoppingStateHolder(
    private val scope: CoroutineScope,
    private val productRepository: ProductRepository = MockRepository(),
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
            val loadData = productRepository.getProducts(offset, pageSize).map { it.toUiModel() }.toImmutableList()
            currentProducts = currentProducts.plus(loadData)
            offset += loadData.size
            canLoadMore = loadData.size == pageSize
            isLoading = false
        }
    }
}
