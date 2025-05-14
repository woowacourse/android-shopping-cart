package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel

fun Goods.toUi(): GoodsUiModel =
    GoodsUiModel(
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
    )

fun GoodsUiModel.toDomain(): Goods =
    Goods(
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
    )
