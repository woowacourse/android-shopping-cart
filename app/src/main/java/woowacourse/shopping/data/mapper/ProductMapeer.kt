package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.presentation.model.ProductModel

fun ProductEntity.toUIModel(): ProductModel = ProductModel(id, title, price, imageUrl, count)

fun ProductModel.toDataModel(): ProductEntity = ProductEntity(id, title, price, imageUrl)
