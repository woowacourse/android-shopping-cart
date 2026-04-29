package woowacourse.shopping.data.dto.response

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

data class CartItemResponseDto(
    val productId: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int
)

fun CartItemResponseDto.toDomainModel(): CartItem {
    return CartItem(
        product = Product(
            id = this.productId,
            imageUrl = this.imageUrl,
            productTitle = ProductTitle(this.name),
            price = Price(this.price)
        ),
        quantity = Quantity(this.quantity)
    )
}
