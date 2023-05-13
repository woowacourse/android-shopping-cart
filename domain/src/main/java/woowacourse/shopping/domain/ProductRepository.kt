package woowacourse.shopping.domain

interface ProductRepository {
    fun findAll(): List<Product>
    fun find(id: Int): Product
    fun findRange(mark: Int, rangeSize: Int): List<Product>
    fun isExistByMark(mark: Int): Boolean
}
