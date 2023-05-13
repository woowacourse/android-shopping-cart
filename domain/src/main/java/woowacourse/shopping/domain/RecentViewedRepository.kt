package woowacourse.shopping.domain

interface RecentViewedRepository {
    fun findAll(): List<Int>
    fun add(id: Int)
    fun remove(id: Int)
}
