package woowacourse.shopping.fixture

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.product.Product

fun getFixtureProducts(count: Int): List<Product> =
    List(count) {
        Product(it + 1L, "사과${it + 1}", "image${it + 1}", 1000 * (it + 1))
    }

fun getFixtureHistoryItems(count: Int): List<ProductHistory> =
    List(count) {
        ProductHistory(it + 1L, it + 1L)
    }

fun getFixtureCartItems(count: Int): List<CartItem> =
    List(count) {
        CartItem(it + 1L, it + 1L, it + 1)
    }
