package woowacourse.shopping.model

data class Cart(
    val items: Map<ProductId, Int>,
) {
    init {
        require(items.values.all { it > 0 }) { "장바구니 수량은 1개 이상이어야 합니다." }
    }
}
