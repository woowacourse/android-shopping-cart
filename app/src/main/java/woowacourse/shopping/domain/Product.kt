package woowacourse.shopping.domain

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    init {
        require(name.isNotBlank()) { "유효하지 않은 상품 이름입니다." }
        require(price >= 0) { "상품 가격이 0원 미만일 수 없습니다." }
    }
}
