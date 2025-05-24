package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product

sealed class CatalogItem(
    val viewType: CatalogType,
) {
    data class ProductItem(
        val productId: Long,
        val imageUrl: String,
        val productName: String,
        val price: Int,
        var quantity: Int = 0,
    ) : CatalogItem(CatalogType.PRODUCT)

    data class RecentProducts(
        val products: List<ProductUiModel>,
    ) : CatalogItem(CatalogType.RECENT_PRODUCT)

    data object LoadMoreItem : CatalogItem(CatalogType.LOAD_MORE)

    enum class CatalogType {
        RECENT_PRODUCT,
        PRODUCT,
        LOAD_MORE,
    }
}

fun Product.toCatalogProductItem() =
    CatalogItem.ProductItem(
        productId = this.id,
        productName = this.name,
        imageUrl = this.imageUrl,
        price = this.price.value,
    )
