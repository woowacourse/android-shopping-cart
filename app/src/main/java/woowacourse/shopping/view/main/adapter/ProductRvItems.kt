package woowacourse.shopping.view.main.adapter

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.main.vm.ProductState

sealed class ProductRvItems(val viewType: ViewType) {
    data class ProductItem(
        val item: Product,
        val quantity: Int,
        val quantityVisible: Boolean,
    ) : ProductRvItems(ViewType.VIEW_TYPE_PRODUCT)

    object LoadItem : ProductRvItems(ViewType.VIEW_TYPE_LOAD)

    enum class ViewType {
        VIEW_TYPE_PRODUCT,
        VIEW_TYPE_LOAD,
    }
}

fun ProductState.toProductRvItems(): ProductRvItems.ProductItem {
    return ProductRvItems.ProductItem(
        item = item,
        quantity = quantity.value,
        quantityVisible = quantityVisible,
    )
}
