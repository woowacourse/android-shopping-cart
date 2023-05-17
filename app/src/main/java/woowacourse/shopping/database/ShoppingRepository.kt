package woowacourse.shopping.database

import model.Product
import model.RecentViewedProduct
import model.ShoppingCartProduct

interface ShoppingRepository {

    fun selectProducts(from: Int, count: Int): List<Product>

    fun selectShoppingCartProducts(from: Int, count: Int): List<ShoppingCartProduct>

    fun selectShoppingCartProductById(id: Int): ShoppingCartProduct

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
