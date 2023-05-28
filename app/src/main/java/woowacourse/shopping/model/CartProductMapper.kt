package woowacourse.shopping.model

import woowacourse.shopping.domain.CartProduct

fun CartProduct.toUIModel(productModel: ProductModel) = CartProductModel(id, productModel, count, check)
