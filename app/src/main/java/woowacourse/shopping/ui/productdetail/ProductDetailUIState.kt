package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.Product

data class ProductDetailUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
) {
    companion object {
        fun from(product: Product): ProductDetailUIState =
            ProductDetailUIState(product.imageUrl, product.name, product.price, product.id)
    }
}
