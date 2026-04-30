package woowacourse.shopping.model

class Product(
    val id: Long,
    private val title: ProductTitle,
    private val price: Price,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    fun getPrice(): Int = price.toInt()

    fun getTitle(): String = title.toString()
}
