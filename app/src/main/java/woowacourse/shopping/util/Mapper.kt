package woowacourse.shopping.util

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.model.CartUiModel
import woowacourse.shopping.feature.model.GoodsUiModel

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

fun Cart.toUi(): CartUiModel =
    CartUiModel(
        id = goods.id,
        name = goods.name,
        price = goods.price,
        thumbnailUrl = goods.thumbnailUrl,
        quantity = quantity,
    )

fun CartUiModel.toDomain(): Cart =
    Cart(
        quantity = quantity,
        goods =
            Goods(
                id = id,
                name = name,
                price = price,
                thumbnailUrl = thumbnailUrl,
            ),
    )
