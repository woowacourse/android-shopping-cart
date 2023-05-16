package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getPartially(size: Int, lastId: Int): List<Product>
}
