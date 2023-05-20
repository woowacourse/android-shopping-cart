package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.Product

interface BasketDataSource {
    interface Local {
        fun getProductByPage(page: DataPageNumber): DataBasket
        fun getProductInBasketByPage(page: DataPageNumber): DataBasket
        fun increaseCartCount(product: Product, count: Int)
        fun decreaseCartCount(product: Product, count: Int)
        fun deleteByProductId(productId: Int)
        fun getProductInBasketSize(): Int
        fun update(basket: DataBasket)
        fun getTotalPrice(): Int
        fun getCheckedProductCount(): Int
        fun getProductInRange(start: DataPageNumber, end: DataPageNumber): DataBasket
        fun removeCheckedProducts()
    }

    interface Remote
}
