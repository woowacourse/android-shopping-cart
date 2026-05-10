package woowacourse.shopping.features.cart

import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

data class CartItemUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int,
)

fun CartItem.toCartUiModel(): CartItemUiModel =
    CartItemUiModel(
        id = product.id,
        name = product.name.value,
        imageUrl = product.imageUrl.value,
        price = product.price.value,
        quantity = quantity.value,
    )

fun CartItemUiModel.toCartItem(): CartItem =
    CartItem(
        product = Product(
            id = id,
            name = ProductName(name),
            imageUrl = ImageUrl(imageUrl),
            price = Price(price),
        ),
        quantity = CartItemQuantity(quantity),
    )

fun CartItemUiModel.getTotalPrice(): Int = price * quantity
