package woowacourse.shopping.mapper

import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

private var tempId = 0L

fun Product.toUiModel() =
    ProductUiModel(
        id = tempId++,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
    )
