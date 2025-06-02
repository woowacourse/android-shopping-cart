package woowacourse.shopping.data.shoppingcart.mapper

import woowacourse.shopping.data.shoppingcart.database.ShoppingCartItemEntity
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingCartItem

fun ShoppingCartItemEntity.toDomain(): ShoppingCartItem {
    return ShoppingCartItem(
        goods =
            Goods.of(
                id = this.id,
                name = this.name,
                price = this.price,
                imageUrl = this.imageUrl,
            ),
        quantity = this.quantity,
    )
}

fun ShoppingCartItem.toEntity(): ShoppingCartItemEntity {
    val goods = this.goods
    return ShoppingCartItemEntity(
        id = goods.id,
        name = goods.name.value,
        price = goods.price.value,
        imageUrl = goods.imageUrl,
        quantity = this.quantity,
    )
}
