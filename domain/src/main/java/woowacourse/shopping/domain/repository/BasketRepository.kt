package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Product

typealias DomainBasketRepository = BasketRepository

interface BasketRepository {
    fun getProductByPage(page: PageNumber): Basket
    fun getProductInBasketByPage(page: PageNumber): Basket
    fun getProductInRange(startPage: PageNumber, endPage: PageNumber): Basket
    fun increaseCartCount(product: Product, count: Int)
    fun decreaseCartCount(product: Product, count: Int)
    fun deleteByProductId(productId: Int)
    fun getProductInBasketSize(): Int
    fun update(basket: Basket)
    fun getTotalPrice(): Int
    fun getCheckedProductCount(): Int
    fun removeCheckedProducts()
}
