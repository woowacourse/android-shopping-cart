package woowacourse.shopping.domain.repository


interface RecentViewedRepository {
    fun findAll(): List<Int>
    fun add(id: Int)
    fun remove(id: Int)
}
