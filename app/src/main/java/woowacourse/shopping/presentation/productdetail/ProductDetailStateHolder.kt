package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository

class ProductDetailStateHolder(
    private val cartRepository: CartRepository,
) {
    fun addToCart(product: Product) {
        cartRepository.addProduct(product)
    }
}
