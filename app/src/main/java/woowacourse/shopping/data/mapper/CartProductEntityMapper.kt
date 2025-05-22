package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.product.catalog.ProductUiModel

fun CartProductEntity.toUiModel(): ProductUiModel =
    with(this) {
        ProductUiModel(
            imageUrl,
            name,
            price,
            quantity,
        )
    }

fun ProductUiModel.toEntity(): CartProductEntity =
    with(this) {
        CartProductEntity(
            0,
            imageUrl,
            name,
            price,
            quantity,
        )
    }
