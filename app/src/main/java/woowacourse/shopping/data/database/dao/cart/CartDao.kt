package woowacourse.shopping.data.database.dao.cart

import woowacourse.shopping.data.model.DataCart
import woowacourse.shopping.data.model.DataCartProduct
import woowacourse.shopping.data.model.DataPage
import woowacourse.shopping.data.model.Product

interface CartDao {
    fun getProductByPage(page: DataPage): DataCart
    fun getProductInCartByPage(page: DataPage): DataCart
    fun insert(product: Product, count: Int)
    fun deleteByProductId(id: Int)
    fun contains(product: Product): Boolean
    fun count(product: Product): Int
    fun getProductInCartSize(): Int
    fun getTotalPrice(): Int
    fun addProductCount(product: Product, count: Int)
    fun minusProductCount(product: Product, count: Int)
    fun update(cartProduct: DataCartProduct)
    fun updateCount(product: Product, count: Int)
    fun getCheckedProductCount(): Int
    fun getProductInRange(start: DataPage, end: DataPage): DataCart
    fun deleteCheckedProducts()
}
