package woowacourse.shopping.mapper

import com.example.domain.model.CartProduct
import woowacourse.shopping.model.CartProductUiModel

fun CartProduct.toPresentation(): CartProductUiModel =
    CartProductUiModel(cartId, product.toPresentation().copy(count = count), checked)

fun CartProductUiModel.toDomain(): CartProduct =
    CartProduct(cartId, productUiModel.toDomain(), productUiModel.count, checked)
