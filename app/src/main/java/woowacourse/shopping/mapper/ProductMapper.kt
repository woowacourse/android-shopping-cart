package woowacourse.shopping.mapper

import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.model.Product

fun Product.toUiModel() =
    ProductUiModel(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
        quantity = 0,
    )
