package woowacourse.shopping.mapper

import woowacourse.shopping.data.cart.CartItem
import woowacourse.shopping.data.recent.ViewedItem
import woowacourse.shopping.product.catalog.ProductUiModel

fun ProductUiModel.toCartItem() =
    CartItem(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
    )

fun ProductUiModel.toViewedItem() =
    ViewedItem(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )
