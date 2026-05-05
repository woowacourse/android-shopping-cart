package woowacourse.shopping.model

data class Product(
    val id: Long,
    private val title: ProductTitle,
    private val price: Price,
    val imageUrl: String,
) {
    fun getPrice(): Int = price.value

    fun getTitle(): String = title.value
}
