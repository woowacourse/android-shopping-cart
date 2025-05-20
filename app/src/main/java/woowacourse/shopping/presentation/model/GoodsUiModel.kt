package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Name
import woowacourse.shopping.domain.model.Price
import java.io.Serializable

data class GoodsUiModel(
    val name: String,
    val price: Int,
    val imageUrl: String,
    val isSelected: Boolean,
) : Serializable

fun GoodsUiModel.toDomain(): Goods =
    Goods(
        name = Name(name),
        price = Price(price),
        imageUrl = imageUrl,
    )

fun Goods.toUiModel(): GoodsUiModel =
    GoodsUiModel(
        name = name.value,
        price = price.value,
        imageUrl = imageUrl,
        isSelected = false,
    )
