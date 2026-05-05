package woowacourse.shopping.ui.model

import woowacourse.shopping.model.Product

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        id = this.id,
        name = this.name.name,
        price = this.price.amount,
        imageUrl = this.imageUrl,
    )
