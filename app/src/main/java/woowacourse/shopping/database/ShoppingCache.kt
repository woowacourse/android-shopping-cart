package woowacourse.shopping.database

import model.CartProduct
import model.Product
import model.RecentViewedProduct

interface ShoppingCache {

    fun selectProducts(from: Int, count: Int): List<Product>

    fun selectShoppingCartProducts(): List<CartProduct>

    fun selectShoppingCartProductById(id: Int): CartProduct

    fun selectProductById(id: Int): Product

    fun insertToShoppingCart(id: Int, count: Int = 1)

    fun deleteFromShoppingCart(id: Int)

    fun insertToRecentViewedProducts(id: Int)

    fun selectRecentViewedProducts(): List<RecentViewedProduct>

    fun selectRecentViewedProductById(id: Int): RecentViewedProduct

    fun deleteFromRecentViewedProducts()

    fun selectLatestViewedProduct(): Product?

    fun getCountOfShoppingCartProducts(): Int

    fun setUpDB()
}
