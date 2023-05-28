package woowacourse.shopping.domain

interface RecentViewedRepository {
    fun findAll(): List<Int>
    fun add(id: Int)
    fun remove(id: Int)
    fun find(id: Int): Int?
    fun findMostRecent(): Int
}
