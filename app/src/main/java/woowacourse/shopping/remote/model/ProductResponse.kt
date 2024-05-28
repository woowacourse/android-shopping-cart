package woowacourse.shopping.remote.model

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int = INIT_QUANTITY_NUM,
    val imageUrl: String,
) {
    companion object {
        const val INIT_QUANTITY_NUM = 0
    }
}
