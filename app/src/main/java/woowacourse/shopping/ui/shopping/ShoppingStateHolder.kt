package woowacourse.shopping.ui.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.MockProductRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.ui.model.ProductUiModel
import woowacourse.shopping.ui.model.toUiModel

class ShoppingStateHolder(
    private val scope: CoroutineScope,
    private val productRepository: ProductRepository = MockProductRepository(),
    initialProducts: ImmutableList<ProductUiModel> = emptyList<ProductUiModel>().toImmutableList(),
    initialCanLoadMore: Boolean = true,
    initialOffset: Int = 0,
    private val onProductsChanged: (List<ProductUiModel>) -> Unit = {},
    private val onCanLoadMoreChanged: (Boolean) -> Unit = {},
    private val onOffsetChanged: (Int) -> Unit = {},
) {
    var currentProducts: ImmutableList<ProductUiModel> by mutableStateOf(initialProducts)
    var canLoadMore by mutableStateOf(initialCanLoadMore)
    var isLoading by mutableStateOf(false)
    private var offset = initialOffset
    private val pageSize = 20

    init {
        if (currentProducts.isEmpty()) loadMore()
    }

    fun loadMore() {
        if (isLoading || !canLoadMore) return
        scope.launch {
            isLoading = true
            val loadData =
                productRepository
                    .getProducts(offset, pageSize)
                    .map { it.toUiModel() }
                    .toImmutableList()
            currentProducts = currentProducts.plus(loadData).toImmutableList()
            offset += loadData.size
            canLoadMore = loadData.size == pageSize
            onProductsChanged(currentProducts)
            onOffsetChanged(offset)
            onCanLoadMoreChanged(canLoadMore)
            isLoading = false
        }
    }
}
