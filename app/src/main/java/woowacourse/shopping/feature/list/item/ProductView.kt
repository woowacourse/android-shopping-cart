package woowacourse.shopping.feature.list.item

sealed interface ProductView {

    data class ProductItem(
        val id: Int,
        val imageUrl: String,
        val name: String,
        val price: Int,
    ) : ProductView

    data class RecentProductsItem(
        val products: List<ProductItem>,
    ) : ProductView

    class MoreItem : ProductView

    companion object {
        const val TYPE_PRODUCT = 0
        const val TYPE_RECENT_PRODUCTS = 1
        const val TYPE_MORE = 2
    }
}
