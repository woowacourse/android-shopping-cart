package woowacourse.shopping.domain.model

import woowacourse.shopping.data.model.CartItemEntity

data class CartItem(val id: Long, val product: Product){
    fun toCartItemEntity(): CartItemEntity{
        return CartItemEntity(
            product = product
        )
    }
}
