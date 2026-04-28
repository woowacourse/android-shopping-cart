package woowacourse.shopping.model

import java.util.UUID

data class Product(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val price: Money,
    val imageResource: Int
)
