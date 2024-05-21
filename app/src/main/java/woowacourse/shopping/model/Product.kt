package woowacourse.shopping.model

data class Product(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val price: Int,
    val quantity: Quantity = Quantity(),
) {
    fun totalPrice(): Int {
        return price * quantity.count
    }
}
