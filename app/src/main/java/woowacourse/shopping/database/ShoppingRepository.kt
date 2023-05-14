package woowacourse.shopping.database

import model.Product
import model.RecentViewedProduct

interface ShoppingRepository {

    fun selectProducts(from: Int, count: Int): List<Product>

    fun selectShoppingCartProducts(from: Int, count: Int): List<Product>

    fun selectProductById(id: Int): Product

    fun insertToShoppingCart(id: Int)

    fun deleteFromShoppingCart(id: Int)

    fun insertToRecentViewedProducts(id: Int)

    fun selectRecentViewedProducts(): List<RecentViewedProduct>

    fun selectRecentViewedProductById(id: Int): RecentViewedProduct

    fun deleteFromRecentViewedProducts()
}
