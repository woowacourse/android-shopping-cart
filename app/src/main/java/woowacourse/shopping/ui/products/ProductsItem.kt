package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.ui.products.ProductsItemViewType.LOAD_MORE
import woowacourse.shopping.ui.products.ProductsItemViewType.PRODUCT

sealed class ProductsItem(
    val viewType: ProductsItemViewType,
) {
    abstract val id: Int

    data class ProductItem(
        val value: CartProduct,
    ) : ProductsItem(PRODUCT) {
        override val id: Int
            get() = value.product.id
    }

    data object LoadMoreItem : ProductsItem(LOAD_MORE) {
        private const val LOAD_MORE_ITEM_ID = -1
        override val id: Int = LOAD_MORE_ITEM_ID
    }
}
