package woowacourse.shopping.data.model

typealias DataBasket = Basket

data class Basket(
    val basketProducts: List<DataBasketProduct> = emptyList(),
)
