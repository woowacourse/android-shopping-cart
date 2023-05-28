package woowacourse.shopping.data.model

data class ProductEntity(
    val id: Long,
    val title: String,
    val price: Int,
    val imageUrl: String
) {
    companion object {
        val errorData: ProductEntity = ProductEntity(-1L, "", 0, "")
    }
}
