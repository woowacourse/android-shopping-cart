package woowacourse.shopping.domain

import java.util.UUID

class Product(val name: String, private val price: Money, val imageUrl: String, val id: String = UUID.randomUUID().toString()) {
    init {
        require(name.isNotBlank()) { "상품 제목은 공백일 수 없습니다." }
    }

    fun hasId(targetId: String): Boolean = this.id == targetId

    fun priceAmount(): Int = price.amount

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return this.id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
