package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product

object HomeMapper {
    fun Product.toProductUiModel(): ProductUiModel {
        return ProductUiModel(
            id = this.id,
            name = this.name,
            itemImage = this.itemImage,
            price = this.price,
        )
    }

    fun Product.toRecentlyViewedProduct(): RecentlyViewedProduct {
        return RecentlyViewedProduct(product = this)
    }
}
