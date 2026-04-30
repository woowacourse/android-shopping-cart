package woowacourse.shopping.feature.shopping.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.feature.shopping.bridge.ShoppingBridge

class ShoppingStateHolder(
    private val scope: CoroutineScope,
    private val shoppingBridge: ShoppingBridge = ShoppingBridge(),
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
            val loadData = shoppingBridge.getProductData(offset, pageSize)
            currentProducts = currentProducts.plus(loadData)
            offset += loadData.size
            canLoadMore = loadData.size == pageSize
            isLoading = false
        }
    }
}
