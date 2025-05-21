package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel

fun Goods.toUi(): GoodsUiModel =
    GoodsUiModel(
        id = id,
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
    )

fun GoodsUiModel.toDomain(): Goods =
    Goods(
        id = id,
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
    )
