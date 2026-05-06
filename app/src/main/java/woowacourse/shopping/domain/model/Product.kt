package woowacourse.shopping.domain.model

import java.util.UUID

class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: ProductName,
    val price: Money,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
