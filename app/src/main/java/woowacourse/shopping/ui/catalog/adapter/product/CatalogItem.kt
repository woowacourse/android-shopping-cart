package woowacourse.shopping.ui.catalog.adapter.product

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.ui.catalog.adapter.product.CatalogItemViewType.LOAD_MORE
import woowacourse.shopping.ui.catalog.adapter.product.CatalogItemViewType.PRODUCT

sealed class CatalogItem(
    val viewType: CatalogItemViewType,
) {
    abstract val id: Int

    data class ProductItem(
        val value: CatalogProduct,
    ) : CatalogItem(PRODUCT) {
        override val id: Int
            get() = value.product.id
    }

    data object LoadMoreItem : CatalogItem(LOAD_MORE) {
        private const val LOAD_MORE_ITEM_ID = -1
        override val id: Int = LOAD_MORE_ITEM_ID
    }
}
