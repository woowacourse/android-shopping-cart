package woowacourse.shopping.model

data class Cart(
    val items: Map<ProductId, Int>,
) {
    init {
        require(items.values.all { it > 0 }) { "장바구니 수량은 1개 이상이어야 합니다." }
    }

    fun add(productId: ProductId): Cart {
        val nextItems =
            items.toMutableMap().apply {
                merge(productId, 1, Int::plus)
            }
        return Cart(nextItems)
    }

    fun delete(productId: ProductId): Cart {
        require(items.containsKey(productId)) { "해당 상품은 장바구니에 존재하지 않습니다." }

        val nextItems =
            items.toMutableMap().apply {
                merge(productId, 1, Int::minus)
                if (getValue(productId) == 0) remove(productId)
            }
        return Cart(nextItems)
    }

    fun count(): Int = items.size
}
