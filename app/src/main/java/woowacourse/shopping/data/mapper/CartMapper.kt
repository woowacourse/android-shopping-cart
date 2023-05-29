package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartEntity
import woowacourse.shopping.presentation.model.CartModel

fun CartEntity.toUIModel(): CartModel =
    CartModel(id, product.toUIModel())

fun CartModel.toDataModel(): CartEntity = CartEntity(id, product.toDataModel())
