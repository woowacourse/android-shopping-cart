package woowacourse.shopping.data.source.cart

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

object CartMapper {
    fun Product.toEntity(): CartEntity =
        CartEntity(
            productId = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            price = this.price,
        )

    fun CartEntity.toUiModel(): CartItem =
        CartItem(
            cartItemId = this.id,
            id = this.productId,
            name = this.name,
            imageUrl = this.imageUrl,
            price = this.price,
        )
}
