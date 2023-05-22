package woowacourse.shopping.domain.fixture

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductCount

internal val ALL_UNCHECKED_CARTS: Cart = Cart(
    items = List(100) { id ->
        CartProduct(
            id = id,
            Product(id = id, name = "상품$id", price = Price(1000), imageUrl = ""),
            selectedCount = ProductCount(1),
            isChecked = false
        )
    }
)

internal val ALL_CHECKED_CARTS: Cart = Cart(
    items = List(100) { id ->
        CartProduct(
            id = id,
            Product(id = id, name = "상품$id", price = Price(1000), imageUrl = ""),
            selectedCount = ProductCount(1),
            isChecked = true
        )
    }
)

internal fun getCart(size: Int): Cart = Cart(
    items = List(size) { id ->
        CartProduct(
            id = id,
            Product(id = id, name = "상품$id", price = Price(1000), imageUrl = ""),
            selectedCount = ProductCount(1),
            isChecked = false
        )
    }
)
