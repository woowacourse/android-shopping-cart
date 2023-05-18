package woowacourse.shopping.database

import model.CartProduct
import model.Product

interface ShoppingRepository {

    fun selectProducts(from: Int, count: Int): List<Product>

    fun selectShoppingCartProducts(from: Int, count: Int): List<CartProduct>

    fun getShoppingCartProductsSize(): Int

    fun selectProductById(id: Int): Product

    fun insertToShoppingCart(id: Int, count: Int, isSelected: Boolean)

    fun deleteFromShoppingCart(id: Int)

    fun insertToRecentViewedProducts(id: Int)

    fun selectRecentViewedProducts(): List<Product>

    fun deleteFromRecentViewedProducts(id: Int)
}
