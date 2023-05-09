package woowacourse.shopping.domain
typealias ID = Int

interface CartRepository {
    fun findAll(): List<ID>
    fun add(id: Int)
    fun remove(id: Int)
}
