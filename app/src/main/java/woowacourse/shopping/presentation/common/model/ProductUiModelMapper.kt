package woowacourse.shopping.presentation.common.model

import woowacourse.shopping.domain.model.Product

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        id = this.id,
        name = this.name.name,
        price = this.price.amount,
        imageUrl = this.imageUrl,
    )
