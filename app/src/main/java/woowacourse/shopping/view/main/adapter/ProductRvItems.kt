package woowacourse.shopping.view.main.adapter

import woowacourse.shopping.view.main.vm.state.ProductState

sealed class ProductRvItems(val viewType: ViewType) {
    data class ProductItem(
        val data: ProductState,
    ) : ProductRvItems(ViewType.VIEW_TYPE_PRODUCT)

    object LoadItem : ProductRvItems(ViewType.VIEW_TYPE_LOAD)

    enum class ViewType {
        VIEW_TYPE_PRODUCT,
        VIEW_TYPE_LOAD,
    }
}
