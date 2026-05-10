package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun CartItemEntity.toDomain(): CartItem =
    CartItem(
        product =
            Product(
                productId = Uuid.parse(productId),
                productName = productName,
                price = Price(price),
                imageUrl = imageUrl,
            ),
        quantity = quantity,
    )

@OptIn(ExperimentalUuidApi::class)
fun Product.toCartItemEntity(quantity: Int): CartItemEntity =
    CartItemEntity(
        productId = productId.toString(),
        productName = productName,
        price = price.value,
        imageUrl = imageUrl,
        quantity = quantity,
    )
