package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.local.cart.CartItemEntity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

fun CartItemEntity.toDomain(): CartItem =
    CartItem(
        product =
            Product(
                id = productId,
                imageUrl = ImageUrl(imageUrl),
                name = ProductName(name),
                price = Price(price),
            ),
        quantity = Quantity(quantity),
    )

fun List<CartItemEntity>.toDomainCart(): Cart =
    Cart(
        cartItems = CartItems(map { it.toDomain() }),
    )

fun Product.toCartItemEntity(quantity: Quantity): CartItemEntity =
    CartItemEntity(
        productId = id,
        name = name.value,
        price = price.value,
        imageUrl = imageUrl.value,
        quantity = quantity.value,
    )
