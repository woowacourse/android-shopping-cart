package woowacourse.shopping.data.shoppingcart.mapper

import woowacourse.shopping.data.shoppingcart.database.ShoppingCartEntity
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingCartItem

fun ShoppingCartEntity.toDomain(): ShoppingCartItem {
    return ShoppingCartItem(
        goods = Goods.of(
            id = this.id,
            name = this.name,
            price = this.price,
            imageUrl = this.imageUrl
        ),
        quantity = this.quantity,
    )
}

fun ShoppingCartItem.toEntity(): ShoppingCartEntity {
    val goods = this.goods
    return ShoppingCartEntity(
        id = goods.id,
        name = goods.name.value,
        price = goods.price.value,
        imageUrl = goods.imageUrl,
        quantity = this.quantity,
    )
}
