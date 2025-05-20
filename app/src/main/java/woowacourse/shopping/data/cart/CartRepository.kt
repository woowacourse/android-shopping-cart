package woowacourse.shopping.data.cart

import woowacourse.shopping.model.product.Product

interface CartRepository {
    val products: MutableList<Product>

    fun add(product: Product)

    fun remove(product: Product)
}
