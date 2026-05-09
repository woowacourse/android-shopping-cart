package woowacourse.shopping.domain

class CartProduct(
    val product: Product,
    val amount: Int,
) {
    init {
        require(amount >= 0) { "수량은 0 이상이여야 합니다." }
    }
}
