package woowacourse.shopping.data.model.response

data class ProductResponse(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val cartQuantity: Int,
)
