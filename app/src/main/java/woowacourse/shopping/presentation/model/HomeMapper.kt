package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product

object HomeMapper {
    fun Product.toProductItem(): ProductItem {
        return ProductItem(this)
    }

    fun Product.toRecentlyViewedProduct(): RecentlyViewedProduct {
        return RecentlyViewedProduct(product = this)
    }
}
