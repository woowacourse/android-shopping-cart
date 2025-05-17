package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product

interface Cart {
    val productsInCart: MutableList<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
