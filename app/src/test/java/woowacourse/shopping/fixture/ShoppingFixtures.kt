package woowacourse.shopping.fixture

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product

fun getFixtureCartedProducts(count: Int): List<CartedProduct> =
    List(count) {
        CartedProduct(
            CartItem(it + 1L, it + 1L, 1),
            Product(it + 1L, "사과${it + 1}", "image${it + 1}", 1000 * (it + 1)),
        )
    }

fun getFixtureCartItems(cartedProducts: List<CartedProduct>): List<CartItem> = cartedProducts.map(CartedProduct::cartItem)

fun getFixtureCartItems(count: Int): List<CartItem> =
    List(count) {
        CartItem(it + 1L, it + 1L, it + 1)
    }

fun getFixtureCartableProducts(count: Int): List<CartableProduct> =
    List(count) {
        CartableProduct(
            Product(it + 1L, "사과${it + 1}", "image${it + 1}", 1000 * (it + 1)),
            null,
        )
    }

fun getFixtureRecentProducts(count: Int): List<RecentProduct> =
    List(count) {
        RecentProduct(
            ProductHistory(it + 1L, it + 1L),
            Product(it + 1L, "사과${it + 1}", "image${it + 1}", 1000 * (it + 1)),
        )
    }

fun getFixtureHistoryItems(recentProducts: List<RecentProduct>): List<ProductHistory> = recentProducts.map(RecentProduct::productHistory)
