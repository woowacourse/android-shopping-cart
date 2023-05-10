package woowacourse.shopping.domain

interface CartRepository {
    fun findAll(): List<CartProduct>
    fun add(id: Int, count: Int)
    fun remove(id: Int)
}
