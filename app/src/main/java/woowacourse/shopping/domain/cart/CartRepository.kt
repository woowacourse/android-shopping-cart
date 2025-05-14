package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

interface CartRepository {
    fun add(product: Product)

    fun fetch(id: Long): Product

    fun fetchAll(callback: (List<Product>) -> Unit)

    fun remove(product: Product)
}
