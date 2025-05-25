package woowacourse.shopping.mapper

import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.data.entity.ShoppingCartItemWithProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}

fun ShoppingCartItem.toEntity(): ShoppingCartItemEntity {
    return ShoppingCartItemEntity(
        id = id,
        productId = product.id,
        quantity = quantity,
    )
}

fun ShoppingCartItemWithProduct.toShoppingCartItem(): ShoppingCartItem {
    return ShoppingCartItem(
        id = shoppingCartItem.id,
        product = product.toProduct(),
        quantity = shoppingCartItem.quantity,
    )
}
