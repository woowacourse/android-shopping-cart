package woowacourse.shopping.ui.productdetail.uistate

import woowacourse.shopping.domain.Product

data class ProductDetailUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
    val isInCart: Boolean
) {
    companion object {
        fun create(product: Product, isInCart: Boolean): ProductDetailUIState =
            ProductDetailUIState(product.imageUrl, product.name, product.price, product.id, isInCart)
    }
}
