package woowacourse.shopping.domain

data class Cart(val products: List<CheckableCartProduct>) {
    operator fun plus(cart: Cart): Cart {
        return Cart(products + cart.products)
    }

    fun selectProduct(checkableCartProduct: CheckableCartProduct, isChecked: Boolean): Cart {
        return Cart(
            products.toMutableList().apply {
                val index = indexOfFirst { it.product == checkableCartProduct.product }
                require(index >= 0) { ERROR_MISSING_CHECKABLE_PRODUCT.format(checkableCartProduct.toString()) }
                this[index] = checkableCartProduct.copy(checked = isChecked)
            }
        )
    }

    fun calculateCheckedProductsCount(): Int {
        return products.filter { it.checked }.size
    }

    fun calculateCheckedProductsPrice(): Int {
        return products.filter { it.checked }.sumOf { it.product.product.price * it.product.amount }
    }

    fun isTotalChecked(): Boolean {
        return products.all { it.checked }
    }

    companion object {
        private const val ERROR_MISSING_CHECKABLE_PRODUCT = "일치하는 상품이 존재하지 않습니다. 찾으려 한 상품 : %s"
    }
}
