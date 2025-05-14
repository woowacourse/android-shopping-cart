package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.model.Product

sealed class Item(
    val viewType: ItemViewType,
) {
    data class ProductItem(
        val value: Product,
    ) : Item(ItemViewType.PRODUCT)

    data object LoadMoreItem : Item(ItemViewType.LOAD_MORE)
}
