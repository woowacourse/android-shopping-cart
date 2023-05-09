package woowacourse.shopping.domain

interface RecentViewedRepository {
    fun findAll(): List<ID>
    fun add(id: ID)
    fun remove(id: ID)
}
