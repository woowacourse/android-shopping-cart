package woowacourse.shopping.domain.model

typealias DomainCartEntity = CartEntity

class CartEntity(
    val id: Int,
    val productId: Int,
    val count: Int,
    val checked: Boolean,
)
