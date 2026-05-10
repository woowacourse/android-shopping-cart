package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product

fun CartItemEntity.toDomain(): CartItem =
    CartItem(
        product =
            Product(
                productId = productId,
                productName = productName,
                price = Price(price),
                imageUrl = imageUrl,
            ),
        quantity = quantity,
    )

fun Product.toCartItemEntity(quantity: Int): CartItemEntity =
    CartItemEntity(
        productId = productId,
        productName = productName,
        price = price.value,
        imageUrl = imageUrl,
        quantity = quantity,
    )
