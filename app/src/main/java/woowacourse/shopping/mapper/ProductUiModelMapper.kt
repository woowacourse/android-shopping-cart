package woowacourse.shopping.mapper

import woowacourse.shopping.data.CartItem
import woowacourse.shopping.product.catalog.ProductUiModel

fun ProductUiModel.toCartItem() =
    CartItem(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
    )
