package woowacourse.shopping.ui.products.adapter.product

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.ui.products.adapter.product.ProductsItemViewType.LOAD_MORE
import woowacourse.shopping.ui.products.adapter.product.ProductsItemViewType.PRODUCT

sealed class ProductsItem(
    val viewType: ProductsItemViewType,
) {
    abstract val id: Int

    data class ProductItem(
        val value: CatalogProduct,
    ) : ProductsItem(PRODUCT) {
        override val id: Int
            get() = value.product.id
    }

    data object LoadMoreItem : ProductsItem(LOAD_MORE) {
        private const val LOAD_MORE_ITEM_ID = -1
        override val id: Int = LOAD_MORE_ITEM_ID
    }
}
