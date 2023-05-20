package woowacourse.shopping.mapper

import com.shopping.domain.CartProduct
import woowacourse.shopping.uimodel.CartProductUIModel

fun CartProductUIModel.toDomain() =
    CartProduct(isPicked, count, product.toDomain())

fun CartProduct.toUIModel() =
    CartProductUIModel(isPicked, count, product.toUIModel())
