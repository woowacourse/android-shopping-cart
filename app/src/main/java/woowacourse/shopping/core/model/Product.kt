package woowacourse.shopping.core.model

import java.util.UUID

class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: ProductName,
    val price: Money,
    val imageUrl: String,
) {
    override fun equals(other: Any?): Boolean = this.id == (other as Product).id

    override fun hashCode(): Int = id.hashCode()
}
