package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.product.catalog.ProductUiModel

fun CartProductEntity.toUiModel(): ProductUiModel =
    ProductUiModel(
        uid,
        imageUrl,
        name,
        price,
        quantity,
    )

fun ProductUiModel.toEntity(): CartProductEntity =
    CartProductEntity(
        id,
        imageUrl,
        name,
        price,
        quantity,
    )
