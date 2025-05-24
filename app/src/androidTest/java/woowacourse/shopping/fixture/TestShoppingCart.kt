@file:Suppress("ktlint")

package woowacourse.shopping.fixture

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

object TestShoppingCart {
    val items:MutableList<ShoppingCartItem> = mutableListOf(
        ShoppingCartItem(
            1,
            DummyProducts.products[0],
            1
        ),
        ShoppingCartItem(
            2,
            DummyProducts.products[1],
            2
        ),
        ShoppingCartItem(
            3,
            DummyProducts.products[2],
            1
        ),
        ShoppingCartItem(
            4,
            DummyProducts.products[3],
            2
        ),
        ShoppingCartItem(
            5,
            DummyProducts.products[4],
            1
        ),
        ShoppingCartItem(
            6,
            DummyProducts.products[5],
            2
        ),
        ShoppingCartItem(
            7,
            DummyProducts.products[6],
            1
        ),
        ShoppingCartItem(
            8,
            DummyProducts.products[7],
            2
        )
    )
}
