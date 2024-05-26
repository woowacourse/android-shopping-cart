package woowacourse.shopping.repository

import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.domain.ImageUrl
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.ShoppingCartItem

fun ProductEntity.toProduct(): Product =
    Product(
        id,
        title,
        Price(price),
        ImageUrl(imageUrl),
    )

fun RecentProductEntity.toRecentProduct(product: Product): RecentProduct =
    RecentProduct(
        product,
        lookDateTime,
    )

fun ShoppingCartItemEntity.toShoppingCartItem(product: Product): ShoppingCartItem =
    ShoppingCartItem(
        product,
        totalQuantity,
    )
