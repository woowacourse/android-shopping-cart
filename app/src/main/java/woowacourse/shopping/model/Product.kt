package woowacourse.shopping.model

class Product(
    val id: String,
    val name: ProductName,
    val price: Money,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    fun getName(): String = name.name

    fun getPrice(): Int = price.amount
}
