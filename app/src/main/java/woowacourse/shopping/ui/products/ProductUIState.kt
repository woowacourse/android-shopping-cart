package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.Product

data class ProductUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
) {
    companion object {
        fun from(product: Product): ProductUIState =
            ProductUIState(product.imageUrl, product.name, product.price)
    }
}
