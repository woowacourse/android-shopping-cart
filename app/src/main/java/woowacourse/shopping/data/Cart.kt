package woowacourse.shopping.data

import woowacourse.shopping.model.products.Product

interface Cart {
    val productsInCart: MutableList<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
