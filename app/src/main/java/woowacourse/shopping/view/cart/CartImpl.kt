package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product

object CartImpl : Cart {
    override val productsInCart = mutableListOf<Product>()

    override fun add(product: Product) {
        productsInCart.add(product)
    }

    override fun remove(product: Product) {
        productsInCart.remove(product)
    }
}
