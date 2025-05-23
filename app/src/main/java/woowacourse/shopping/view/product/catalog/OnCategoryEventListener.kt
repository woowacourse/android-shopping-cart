package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.domain.Product

interface OnCategoryEventListener {
    fun onItemClick(product: Product)

    fun onInitPlusButtonClick(productId: Long)
}
