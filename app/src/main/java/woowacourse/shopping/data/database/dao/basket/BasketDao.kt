package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.Product

interface BasketDao {
    fun getProductByPage(page: DataPageNumber): DataBasket
    fun getProductInBasketByPage(page: DataPageNumber): DataBasket
    fun insert(product: Product, count: Int)
    fun deleteByProductId(id: Int)
    fun contains(product: Product): Boolean
    fun count(product: Product): Int
    fun updateCount(product: Product, count: Int)
    fun getProductInBasketSize(): Int
    fun getTotalPrice(): Int
    fun addProductCount(product: Product, count: Int)
}
