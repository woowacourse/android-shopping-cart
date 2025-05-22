package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.product.catalog.ProductUiModel

fun CartProductEntity.toUiModel(): ProductUiModel =
    with(this) {
        ProductUiModel(
            uid,
            imageUrl,
            name,
            price,
            quantity,
        )
    }

fun ProductUiModel.toEntity(): CartProductEntity =
    with(this) {
        CartProductEntity(
            id,
            imageUrl,
            name,
            price,
            quantity,
        )
    }
