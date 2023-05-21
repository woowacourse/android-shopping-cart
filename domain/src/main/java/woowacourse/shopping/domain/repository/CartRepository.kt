package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Page
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getProductByPage(page: Page): Cart
    fun getProductInCartByPage(page: Page): Cart
    fun getProductInRange(startPage: Page, endPage: Page): Cart
    fun increaseCartCount(product: Product, count: Int)
    fun decreaseCartCount(product: Product, count: Int)
    fun deleteByProductId(productId: Int)
    fun getProductInCartSize(): Int
    fun update(cart: Cart)
    fun getTotalPrice(): Int
    fun getCheckedProductCount(): Int
    fun removeCheckedProducts()
}
