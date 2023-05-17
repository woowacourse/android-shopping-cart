package woowacourse.shopping.mapper

import com.example.domain.model.CartProduct
import woowacourse.shopping.model.CartProductUiModel

fun CartProduct.toPresentation(): CartProductUiModel =
    CartProductUiModel(product.toPresentation(count), count, isSelected)

fun CartProductUiModel.toDomain(): CartProduct =
    CartProduct(productUiModel.toDomain(), count, isSelected)
