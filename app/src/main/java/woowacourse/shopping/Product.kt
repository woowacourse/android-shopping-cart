package woowacourse.shopping

class Product(
    val id: Long,
    val title: String,
    val price: Int,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Product) return false
        return id == other.id
    }
}
