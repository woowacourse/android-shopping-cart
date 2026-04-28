package woowacourse.shopping.domain

import java.util.UUID

class Product(
    private val name: String,
    private val price: Money,
    private val imageUrl: String,
    private val id: String = UUID.randomUUID().toString(),
) {
    init {
        require(name.isNotBlank()) { "상품 제목은 공백일 수 없습니다." }
    }

    fun isSame(other: Product): Boolean = this.id == other.id
}
