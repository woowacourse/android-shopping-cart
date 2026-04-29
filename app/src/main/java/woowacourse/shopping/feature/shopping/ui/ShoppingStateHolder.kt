package woowacourse.shopping.feature.shopping.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.feature.shopping.bridge.ShoppingBridge
import kotlin.math.min

class ShoppingStateHolder(
    private val shoppingBridge: ShoppingBridge = ShoppingBridge(),
) {
    var currentProducts by mutableStateOf(emptyList<ProductUiModel>())
    var isCanLoadMore by mutableStateOf(true)
    private var offset = 0
    private val pageSize = 20

    init {
        addProducts()
        isCanLoadMore()
    }

    fun isCanLoadMore() {
        isCanLoadMore = offset < shoppingBridge.products.size
    }

    fun addProducts() {
        val fromIndex = offset
        offset = min(offset + pageSize, shoppingBridge.products.size)
        currentProducts = currentProducts + shoppingBridge.products.subList(fromIndex, offset)
        isCanLoadMore()
    }
}
