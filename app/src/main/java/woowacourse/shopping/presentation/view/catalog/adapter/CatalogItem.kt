package woowacourse.shopping.presentation.view.catalog.adapter

import woowacourse.shopping.domain.model.CartItem

sealed class CatalogItem(
    val viewType: CatalogType,
) {
    data class ProductItem(
        val productId: Long,
        val imageUrl: String,
        val productName: String,
        val price: Int,
        var quantity: Int,
    ) : CatalogItem(CatalogType.PRODUCT)

    data object LoadMoreItem : CatalogItem(CatalogType.LOAD_MORE)

    enum class CatalogType {
        PRODUCT,
        LOAD_MORE,
    }
}

fun CartItem.toCatalogProductItem() =
    CatalogItem.ProductItem(
        productId = product.id,
        productName = product.name,
        imageUrl = product.imageUrl,
        price = product.price.value,
        quantity = quantity,
    )
