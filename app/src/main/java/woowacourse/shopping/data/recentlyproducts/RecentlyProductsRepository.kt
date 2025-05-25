package woowacourse.shopping.data.recentlyproducts

interface RecentlyProductsRepository {
    fun insert(productId: Long)

    fun getFirst(): Long

    fun getAll(): List<Long>
}
