package woowacourse.shopping.data.product.entity

import woowacourse.shopping.domain.product.CartItem

data class CartItemEntity(
    val id: Long,
    val name: String,
    val price: Int,
) {
    fun toDomain(): CartItem =
        CartItem(
            id,
            name,
            price,
        )

    companion object {
        fun CartItem.toEntity(): CartItemEntity =
            CartItemEntity(
                id,
                name,
                price,
            )
    }
}
