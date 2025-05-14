package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product

object Cart {
    val productsInCart = mutableListOf<Product>()

    fun add(product: Product) {
        productsInCart.add(product)
    }

    fun remove(product: Product) {
        productsInCart.remove(product)
    }
}
