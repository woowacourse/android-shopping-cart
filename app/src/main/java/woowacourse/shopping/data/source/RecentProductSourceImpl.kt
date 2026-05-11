package woowacourse.shopping.data.source

object RecentProductSourceImpl : RecentProductSource {
    private val productIds = mutableListOf("1", "2", "3")

    override fun getRecentProductIds(): List<String> = productIds.reversed()

    override fun addRecentProductId(productId: String) {
        if (productIds.contains(productId)) {
            productIds.remove(productId)
        }

        productIds.add(productId)
    }

    override fun getLastViewProductId(): String = productIds.first()
}
