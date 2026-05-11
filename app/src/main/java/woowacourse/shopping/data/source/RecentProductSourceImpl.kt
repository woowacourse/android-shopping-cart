package woowacourse.shopping.data.source

object RecentProductSourceImpl : RecentProductSource {
    private val productIds = mutableListOf("1", "2", "3")

    override fun getRecentProductIds(): List<String> = productIds

    override fun addRecentProductId(productId: String) {
        productIds.add(productId)
    }

    override fun getLastViewProductId(): String = productIds.last()
}
