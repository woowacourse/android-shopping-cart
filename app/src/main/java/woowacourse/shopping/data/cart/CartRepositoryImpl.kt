package woowacourse.shopping.data.cart

import woowacourse.shopping.model.product.Product

object CartRepositoryImpl : CartRepository {
    override val products = mutableListOf<Product>()

    override fun add(product: Product) {
        products.add(product)
    }

    override fun remove(product: Product) {
        products.remove(product)
    }
}
