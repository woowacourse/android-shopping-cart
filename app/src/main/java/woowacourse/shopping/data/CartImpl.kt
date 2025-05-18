package woowacourse.shopping.data

import woowacourse.shopping.model.products.Product

object CartImpl : Cart {
    override val products = mutableListOf<Product>()

    override fun add(product: Product) {
        products.add(product)
    }

    override fun remove(product: Product) {
        products.remove(product)
    }
}
