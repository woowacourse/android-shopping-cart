package woowacourse.shopping

class CartProductInfoList(cartProductInfos: List<CartProductInfo>) {
    private val _items = cartProductInfos.toMutableList()
    val items = _items.toList()

    val size get(): Int = _items.size
    var count: Int = items.sumOf { it.count }
}
