package woowacourse.shopping.fixture

import woowacourse.shopping.domain.product.CartItem

val CartItem_LUCKY = CartItem(id = 1, name = "럭키", price = 4000)
val CartItem_AIDA = CartItem(id = 2, name = "아이다", price = 700)
val CartItem_SEOLBACK = CartItem(id = 3, name = "설백", price = 1_000)
val CartItem_JUMMA = CartItem(id = 4, name = "줌마", price = 1_000)
val CartItem_JACKSON = CartItem(id = 5, name = "잭슨", price = 20_000)

val CartItems: List<CartItem> =
    listOf(CartItem_LUCKY, CartItem_AIDA, CartItem_SEOLBACK, CartItem_JUMMA, CartItem_JACKSON)
