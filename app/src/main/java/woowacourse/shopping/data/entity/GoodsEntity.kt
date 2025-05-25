package woowacourse.shopping.data.entity

import woowacourse.shopping.domain.model.Goods

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
    Goods.of(
        id = id.toInt(),
        name = name,
        price = price.toInt(),
        imageUrl = imageUrl,
    )
