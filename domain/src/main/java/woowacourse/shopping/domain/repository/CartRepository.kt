package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun getProductByPage(page: PageNumber): Cart
    fun getProductInCartByPage(page: PageNumber): Cart
    fun getProductInRange(startPage: PageNumber, endPage: PageNumber): Cart
    fun increaseCartCount(product: Product, count: Int)
    fun decreaseCartCount(product: Product, count: Int)
    fun deleteByProductId(productId: Int)
    fun getProductInCartSize(): Int
    fun update(cart: Cart)
    fun getTotalPrice(): Int
    fun getCheckedProductCount(): Int
    fun removeCheckedProducts()
}
