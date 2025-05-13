package woowacourse.shopping.mapper

import woowacourse.shopping.data.CartProduct
import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )

fun ProductUiModel.toEntity(): CartProduct =
    CartProduct(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )
