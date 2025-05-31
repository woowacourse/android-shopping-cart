package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

sealed class CartAdapterItem {
    data class Product(
        val product: ProductUiModel,
    ) : CartAdapterItem()

    data class PaginationButton(
        val hasPrevious: Boolean,
        val hasNext: Boolean,
    ) : CartAdapterItem()
}
