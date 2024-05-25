package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartableProduct

interface ProductRepository {
    fun fetchSinglePage(page: Int): List<CartableProduct>

    fun fetchProduct(id: Long): CartableProduct
}
