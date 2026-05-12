package woowacourse.shopping.ui.model.mapper

import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.model.ProductUiModel

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        id = this.id,
        name = this.name.name,
        price = this.price.amount,
        imageUrl = this.imageUrl,
    )
