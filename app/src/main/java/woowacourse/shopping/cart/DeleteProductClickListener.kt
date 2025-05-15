package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

fun interface DeleteProductClickListener {
    fun onClick(cartProduct: ProductUiModel)
}
