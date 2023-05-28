package woowacourse.shopping.data.server

import woowacourse.shopping.domain.Product

interface ProductService {
    fun findAll(): List<Product>
    fun find(id: Int): Product
    fun findRange(mark: Int, rangeSize: Int): List<Product>
    fun isExistByMark(mark: Int): Boolean
}
