package woowacourse.shopping.model

class CartProducts(cartProducts: List<CartProduct> = listOf()) {
    private val _items = cartProducts.toMutableList()
    val items get() = _items.toList()

    val size get(): Int = _items.size
    fun isProductSelectedByRange(startIndex: Int, productSize: Int): Boolean {
        if (startIndex + productSize > size) {
            return (startIndex until size).all { _items[it].isChecked }
        }
        return (startIndex until startIndex + productSize).all { _items[it].isChecked }
    }

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
        addTargetProductCount(targetIndex, count)
    }

    private fun addTargetProductCount(targetIndex: Int, count: Int) {
        val targetProduct = _items[targetIndex]
        _items[targetIndex] = targetProduct + count
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
        if (targetProduct.quantity - count < MIN_COUNT) {
            return
        }
        _items[targetIndex] = targetProduct - count
    }

    fun changeSelectedProduct(product: Product) {
        val targetIndex = _items.indexOfLast { it.product == product }
        val targetProduct = _items[targetIndex]
        _items[targetIndex] = if (targetProduct.isChecked) {
            targetProduct.unselect()
        } else {
            targetProduct.select()
        }
    }

    fun selectProductsRange(startIndex: Int, productSize: Int) {
        if (startIndex + productSize > size) {
            (startIndex until size).forEach { _items[it] = _items[it].select() }
            return
        }
        (startIndex until startIndex + productSize).forEach { _items[it] = _items[it].select() }
    }

    fun unselectProductsRange(startIndex: Int, productSize: Int) {
        if (startIndex + productSize > size) {
            (startIndex until size).forEach { _items[it] = _items[it].unselect() }
            return
        }
        (startIndex until startIndex + productSize).forEach { _items[it] = _items[it].unselect() }
    }

    fun getSelectedProductsPrice(): Int {
        return _items.filter { it.isChecked }.sumOf { it.getTotalPrice() }
    }

    fun getSelectedProductsTotalCount(): Int {
        return _items.filter { it.isChecked }.sumOf { it.quantity }
    }

    companion object {
        private const val NOT_FOUND = -1
        private const val MIN_COUNT = 1
    }
}
