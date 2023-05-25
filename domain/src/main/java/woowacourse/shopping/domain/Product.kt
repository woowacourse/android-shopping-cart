package woowacourse.shopping.domain

class Product(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val price: Int,
) {
    override fun equals(other: Any?): Boolean = if (other is Product) id == other.id else false

    override fun hashCode(): Int = id.hashCode()
}
