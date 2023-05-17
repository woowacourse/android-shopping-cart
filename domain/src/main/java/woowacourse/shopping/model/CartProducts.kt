package woowacourse.shopping.model

class CartProducts(products: List<CartProduct> = listOf()) {
    private val _items = products.toMutableList()
    val items get() = _items.toList()

    val size get(): Int = _items.size

    fun getProductsInRange(startIndex: Int, productSize: Int): CartProducts {
        if (startIndex + productSize > size) {
            return CartProducts(_items.subList(startIndex, size))
        }
        return CartProducts(_items.subList(startIndex, startIndex + productSize))
    }

    fun addProducts(products: List<CartProduct>) {
        _items.addAll(products)
    }

    fun addProductByCount(product: Product, count: Int) {
        val targetIndex = _items.indexOfFirst { it.product == product }
        if (targetIndex == NOT_FOUND) {
            _items.add(CartProduct(product, count))
            return
        }
        _items[targetIndex] =
            CartProduct(_items[targetIndex].product, _items[targetIndex].count + count)
    }

    fun deleteProduct(product: Product) {
        _items.removeAll { it.product == product }
    }

    fun subProductByCount(product: Product, count: Int) {
        val targetIndex = _items.indexOfFirst { it.product == product }
        when {
            targetIndex == NOT_FOUND -> {}
            _items[targetIndex].count <= 1 -> _items.removeAt(targetIndex)
            else -> _items[targetIndex] =
                CartProduct(_items[targetIndex].product, _items[targetIndex].count - count)
        }
    }

    companion object {
        private const val NOT_FOUND = -1
    }
}
