package woowacourse.shopping.data.datasource.cartdatasource

import com.example.domain.CartProduct
import com.example.domain.Product

interface CartDataSource {
    fun getAll(): List<CartProduct>
    fun getCartProducts(limit: Int, offset: Int): List<CartProduct>
    fun addColumn(product: Product, count: Int)
    fun updateColumn(item: CartProduct)
    fun deleteColumn(cartProduct: CartProduct)
    fun findProductById(id: Int): CartProduct?
}
