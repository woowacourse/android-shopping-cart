package woowacourse.shopping.model

typealias UiCart = Cart

class Cart(
    val cartProducts: List<UiCartProduct> = emptyList(),
)
