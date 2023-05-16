package woowacourse.shopping.mapper

import com.shopping.domain.CartProduct
import woowacourse.shopping.uimodel.CartProductUIModel

fun CartProductUIModel.toDomain() =
    CartProduct(count, product.toDomain())

fun CartProduct.toUIModel() =
    CartProductUIModel(count, product.toUIModel())
