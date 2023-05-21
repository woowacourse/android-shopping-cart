package woowacourse.shopping.data.model

typealias DataCart = Cart

data class Cart(
    val cartProducts: List<DataCartProduct> = emptyList(),
)
