package woowacourse.shopping.mapper

import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        id = 0,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
    )
