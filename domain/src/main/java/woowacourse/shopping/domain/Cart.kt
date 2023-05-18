package woowacourse.shopping.domain

data class Cart(val products: List<CheckableCartProduct>) {

    operator fun plus(cart: Cart): Cart {
        return Cart(products + cart.products)
    }

    fun checkProduct(checkableCartProduct: CheckableCartProduct, isChecked: Boolean): Cart {
        return Cart(
            products.toMutableList().apply {
                val index = indexOfFirst { it.product == checkableCartProduct.product }
                this[index] = checkableCartProduct.copy(checked = isChecked)
            }
        )
    }

    fun calculateCheckedProductsPrice(): Int {
        return products.filter { it.checked }.sumOf { it.product.product.price * it.product.amount }
    }
}
