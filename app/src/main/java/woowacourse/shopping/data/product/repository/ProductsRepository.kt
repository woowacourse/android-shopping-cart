package woowacourse.shopping.data.product.repository

import woowacourse.shopping.domain.product.CartItem

interface ProductsRepository {
    fun load(onLoad: (Result<List<CartItem>>) -> Unit)
}
