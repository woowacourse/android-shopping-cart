package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.ProductInCart

object HomeMapper {
    fun ProductInCart.toProductItem(): ProductItem {
        return ProductItem(this)
    }
}
