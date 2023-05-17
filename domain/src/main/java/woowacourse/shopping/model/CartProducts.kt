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
        val targetIndex = _items.indexOfLast { it.product == product }
        if (targetIndex == NOT_FOUND) {
            _items.add(CartProduct(product, count, true))
            return
        }
        addTargetProductCount(targetIndex, count)
    }

    private fun addTargetProductCount(targetIndex: Int, count: Int) {
        val targetProduct = _items[targetIndex]
        _items[targetIndex] = targetProduct.plusCount(count)
    }

    fun deleteProduct(product: Product) {
        _items.removeIf { it.product == product }
    }

    fun subProductByCount(product: Product, count: Int) {
        val targetIndex = _items.indexOfLast { it.product == product }
        if (targetIndex == NOT_FOUND) {
            return
        }
        subTargetProductCount(targetIndex, count)
    }

    private fun subTargetProductCount(targetIndex: Int, count: Int) {
        val targetProduct = _items[targetIndex]
        if (targetProduct.count - count < 1) {
            _items.removeAt(targetIndex)
            return
        }
        _items[targetIndex] = targetProduct.subCount(count)
    }

    fun changeSelectedProduct(product: Product) {
        val targetIndex = _items.indexOfLast { it.product == product }
        println("나 왜불림")
        val targetProduct = _items[targetIndex]
        _items[targetIndex] = if (targetProduct.isSelected) {
            targetProduct.unselect()
        } else {
            targetProduct.select()
        }
    }

    fun getSelectedProductsPrice(): Int {
        return _items.filter { it.isSelected }.sumOf { it.getTotalPrice() }
    }

    fun getSelectedProductsCount(): Int {
        return _items.filter { it.isSelected }.sumOf { it.count }
    }

    companion object {
        private const val NOT_FOUND = -1
    }
}
