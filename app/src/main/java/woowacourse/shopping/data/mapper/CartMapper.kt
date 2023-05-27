package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartEntity
import woowacourse.shopping.data.respository.product.ProductsDao
import woowacourse.shopping.presentation.model.CartModel

fun CartEntity.toUIModel(): CartModel =
    CartModel(id, (ProductsDao().getDataById(productId) ?: ProductsDao().getErrorData()).toUIModel())

fun CartModel.toDataModel(): CartEntity = CartEntity(id, product.id)
