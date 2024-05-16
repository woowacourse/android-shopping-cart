package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun cartProducts(): List<Product>

    fun deleteCartProduct(id: Long)
}

