package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartProductEntity
import woowacourse.shopping.data.respository.product.ProductsDao
import woowacourse.shopping.presentation.model.CartProductModel

fun CartProductEntity.toUIModel() = CartProductModel(
    product = (ProductsDao.getDataById(productId) ?: ProductsDao.getErrorData()).toUIModel(),
    count = count,
    isChecked = isSelected
)
