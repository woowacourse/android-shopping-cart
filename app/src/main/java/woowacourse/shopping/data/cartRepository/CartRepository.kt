package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun addProduct(product: Product)
}
