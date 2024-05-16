package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun cartProducts(): List<Product>

    fun deleteCartProduct(id: Long)
}

