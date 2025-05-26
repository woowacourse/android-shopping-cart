package woowacourse.shopping.ui.productlist

data class ProductListUiModel(
    val productViewTypes: List<ProductListViewType> = emptyList()
) {
    var pageNumber: Int = 0
        private set
    private val originProducts: List<ProductListViewType.ProductItemType>
        get() = productViewTypes.filterIsInstance<ProductListViewType.ProductItemType>()

    private val loadMore = ProductListViewType.LoadMoreType

    fun addProducts(
        newProducts: List<ProductListViewType>,
        isAddLoadMore: Boolean
    ): ProductListUiModel {
        return ProductListUiModel(
            if (isAddLoadMore) originProducts + newProducts + loadMore else originProducts + newProducts
        )
    }

    fun updateQuantityByProductId(
        productId: Long,
        delta: Int
    ): ProductListUiModel {
        val updated = productViewTypes.map { productViewType ->
            if (productViewType is ProductListViewType.ProductItemType && productViewType.product.id == productId) {
                ProductListViewType.ProductItemType(
                    productViewType.product, productViewType.quantity + delta
                )
            } else {
                productViewType
            }
        }
        return ProductListUiModel(updated)
    }

    fun getQuantityByProductId(productId: Long): Int {
        val viewType = productViewTypes.find { productViewType ->
            productViewType is ProductListViewType.ProductItemType && productViewType.product.id == productId
        } ?: return 0
        return (viewType as ProductListViewType.ProductItemType).quantity
    }

    fun pageUp() {
        pageNumber++
    }

}
