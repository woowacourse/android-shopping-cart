package woowacourse.shopping.data.entity

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Name
import woowacourse.shopping.domain.model.Price

class GoodsEntity(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
)

fun Goods.toEntity(): GoodsEntity =
    GoodsEntity(
        id = id.toString(),
        name = name.value,
        price = price.value.toString(),
        imageUrl = imageUrl,
    )

fun GoodsEntity.toGoods(): Goods =
    Goods(
        id = id.toInt(),
        name = Name(name),
        price = Price(price.toInt()),
        imageUrl = imageUrl,
    )
