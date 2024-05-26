package woowacourse.shopping.database

import woowacourse.shopping.database.recentviewedproducts.RecentlyViewedProductEntity
import woowacourse.shopping.database.shoppingcart.ShoppingCartItemEntity
import woowacourse.shopping.domain.ImageUrl
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem

fun ShoppingCartItemEntity.toShoppingCartItem(): ShoppingCartItem {
    return ShoppingCartItem(
        Product(
            id = productId,
            name = name,
            price = Price(price),
            imageUrl = ImageUrl(imageUrl),
        ),
        quantity = quantity,
    )
}

fun RecentlyViewedProductEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        price = Price(price),
        imageUrl = ImageUrl(imageUrl),
    )
}
