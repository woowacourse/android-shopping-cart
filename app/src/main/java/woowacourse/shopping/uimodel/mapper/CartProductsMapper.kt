package woowacourse.shopping.uimodel.mapper

import com.shopping.domain.CartProducts
import woowacourse.shopping.uimodel.CartProductsUIModel

fun CartProducts.toUIModel(): CartProductsUIModel = CartProductsUIModel(products.map { it.toUIModel() })

fun CartProductsUIModel.toDomain(): CartProducts = CartProducts(products.map { it.toDomain() })
