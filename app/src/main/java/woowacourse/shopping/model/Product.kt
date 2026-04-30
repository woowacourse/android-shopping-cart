package woowacourse.shopping.model

class Product(
    val id: Long,
    val title: String,
    val price: Price,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
