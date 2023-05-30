package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.RecentProductEntity
import woowacourse.shopping.data.respository.product.ProductsDao
import woowacourse.shopping.presentation.model.RecentProductModel

fun RecentProductEntity.toUIModel(): RecentProductModel = RecentProductModel(
    id,
    (ProductsDao().getDataById(productId) ?: ProductsDao().getErrorData()).toUIModel()
)

fun RecentProductModel.toDataModel(): RecentProductEntity = RecentProductEntity(id, product.id)
