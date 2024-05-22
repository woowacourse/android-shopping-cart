package woowacourse.shopping.uimodel

import woowacourse.shopping.domain.Product
import woowacourse.shopping.uimodel.ProductUiModel

fun Product.toProductUiModel(): ProductUiModel =
    ProductUiModel(
        id,
        name,
        price.value,
        imageUrl.url,
    )
