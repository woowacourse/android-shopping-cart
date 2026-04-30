package woowacourse.shopping.domain

import java.util.UUID

class Product(val name: String, private val price: Money, val imageUrl: String, val id: String = UUID.randomUUID().toString()) {
    init {
        require(name.isNotBlank()) { "상품 제목은 공백일 수 없습니다." }
    }

    fun isSame(other: Product): Boolean = this.id == other.id

    fun isSameId(other: String): Boolean = this.id == other

    fun priceAmount(): Int = price.amount
}
