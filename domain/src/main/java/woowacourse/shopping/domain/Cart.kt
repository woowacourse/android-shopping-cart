package woowacourse.shopping.domain

data class Cart(val products: List<CheckableCartProduct>) {

    operator fun plus(cart: Cart): Cart {
        return Cart(products + cart.products)
    }

    fun checkProduct(checkableCartProduct: CheckableCartProduct, isChecked: Boolean): Cart {
        return Cart(
            products.toMutableList().apply {
                this[indexOf(checkableCartProduct)] = checkableCartProduct.copy(checked = isChecked)
            }
        )
    }
}
