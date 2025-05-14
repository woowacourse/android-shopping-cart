package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel

fun GoodsUiModel.toEntity(): CartEntity = CartEntity(name = name, price = price, thumbnailUrl = thumbnailUrl)

fun CartEntity.toDomain(): Goods = Goods(name = name, price = price, thumbnailUrl = thumbnailUrl)

fun List<CartEntity>.toDomainList(): List<Goods> = this.map { it.toDomain() }
