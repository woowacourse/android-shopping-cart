package woowacourse.shopping.data.source

interface RecentProductSource {
    fun getRecentProductIds(): List<String>

    fun addRecentProductId(productId: String)

    fun getLastViewProductId(): String
}
