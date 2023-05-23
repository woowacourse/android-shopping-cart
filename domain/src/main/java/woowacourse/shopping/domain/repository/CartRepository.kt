package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartEntity
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getAllCartEntities(): List<CartEntity>
    fun getCartEntity(productId: Int): CartEntity
    fun increaseCartCount(product: Product, count: Int)
    fun decreaseCartCount(product: Product, count: Int)
    fun deleteByProductId(productId: Int)
    fun getProductInCartSize(): Int
    fun update(cartProducts: List<CartProduct>)
    fun getCheckedProductCount(): Int
    fun removeCheckedProducts()
}
