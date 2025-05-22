package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.uimodel.CartItem

object CartMapper {
    fun CartEntity.toDomain(): Product =
        Product(
            id = this.productId,
            name = this.name,
            imageUrl = this.imageUrl,
            price = this.price,
        )

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
