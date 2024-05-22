package woowacourse.shopping.productdetail

data class ProductUiModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val cartItemCount: Int = 0,
) {
    val totalPrice = price * cartItemCount
}
