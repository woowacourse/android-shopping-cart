package woowacourse.shopping

data class CartProductInfo(val product: Product, val count: Int, val isOrdered: Boolean = false) {
    var totalPrice: Int = product.price.value * count
    fun setCount(newCount: Int): CartProductInfo {
        return CartProductInfo(product, newCount, isOrdered)
    }

    fun setOrderState(newIsOrdered: Boolean): CartProductInfo {
        return CartProductInfo(product, count, newIsOrdered)
    }
}
