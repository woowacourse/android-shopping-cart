package woowacourse.shopping.domain.cartsystem

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Price

data class CartSystemProduct(val cartProduct: CartProduct, val price: Price)
