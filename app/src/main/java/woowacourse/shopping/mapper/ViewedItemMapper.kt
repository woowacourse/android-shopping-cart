package woowacourse.shopping.mapper

import woowacourse.shopping.data.recent.ViewedItem
import woowacourse.shopping.product.catalog.ProductUiModel

fun ViewedItem.toUiModel() =
    ProductUiModel(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )
