package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findAll(): List<Product>

    fun find(id: Int): Product
    fun findRange(mark: Int, rangeSize: Int): List<Product>
    fun isExistByMark(mark: Int): Boolean
}
