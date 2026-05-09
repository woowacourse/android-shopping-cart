package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

fun CartItemEntity.toDomain(): CartItem =
    CartItem(
        product =
            Product(
                id = id,
                name = ProductName(name),
                price = Money(price),
                imageUrl = imageUrl,
            ),
        quantity = quantity,
    )

fun CartItem.toEntity(timestamp: Long): CartItemEntity =
    CartItemEntity(
        id = product.id,
        name = product.getName(),
        price = product.getPrice(),
        imageUrl = product.imageUrl,
        quantity = quantity,
        timestamp = timestamp,
    )
