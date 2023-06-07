package woowacourse.shopping.ui.productdetail.uistate

import woowacourse.shopping.domain.Product

data class ProductDetailUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
    val cartCount: Int,
) {
    companion object {
        fun from(product: Product, count: Int): ProductDetailUIState =
            ProductDetailUIState(product.imageUrl, product.name, product.price, product.id, count)
    }
}
