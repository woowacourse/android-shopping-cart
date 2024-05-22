package woowacourse.shopping.productlist

import woowacourse.shopping.domain.Product

fun Product.toProductUiModel(): ProductUiModel =
    ProductUiModel(
        id,
        name,
        price.value,
        imageUrl.url,
    )
