package woowacourse.shopping.model

class Cart(
    items: Map<Product, Int>
) {
    private val value = items.toMutableMap()

    fun add(item: Product): Cart {
        value.merge(item, 1, Int::plus)
        return Cart(value)
    }

    fun delete(item: Product): Cart {
        require(value.containsKey(item)) { "해당 상품은 장바구니에 존재하지 않습니다." }

        value.merge(item, 1, Int::minus)
        if (value.getValue(item) == 0) value.remove(item)
        return Cart(value)
    }

    fun showAll(): Map<Product, Int> {
        return value.toMap()
    }
}
