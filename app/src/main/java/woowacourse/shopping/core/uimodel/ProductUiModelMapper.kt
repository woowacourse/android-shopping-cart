package woowacourse.shopping.core.uimodel

import woowacourse.shopping.core.model.Product

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        id = this.id,
        name = this.name.name,
        price = this.price.amount,
        imageUrl = this.imageUrl,
    )
