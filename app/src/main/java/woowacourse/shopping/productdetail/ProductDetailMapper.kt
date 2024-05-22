package woowacourse.shopping.productdetail

import woowacourse.shopping.domain.Product

fun Product.toProductUiModel(cartItemCount: Int): ProductUiModel =
    ProductUiModel(
        id,
        name,
        price.value,
        imageUrl.url,
        cartItemCount,
    )
