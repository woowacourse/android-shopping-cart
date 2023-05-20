package woowacourse.shopping.model

typealias UiBasket = Basket

class Basket(
    val basketProducts: List<UiBasketProduct> = emptyList(),
)
