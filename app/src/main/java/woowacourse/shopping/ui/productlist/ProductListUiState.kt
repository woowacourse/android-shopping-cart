package woowacourse.shopping.ui.productlist

data class ProductListUiState(
    val pageNumber: Int = 0,
    val productViewTypes: List<ProductListViewType> = emptyList()
) {
    private val originProducts: List<ProductListViewType.ProductItemType>
        get() = productViewTypes.filterIsInstance<ProductListViewType.ProductItemType>()

    private val loadMore = ProductListViewType.LoadMoreType

    fun addProducts(
        newProducts: List<ProductListViewType>,
        isAddLoadMore: Boolean
    ): ProductListUiState {
        return ProductListUiState(
            pageNumber + 1,
            if (isAddLoadMore) originProducts + newProducts + loadMore else originProducts + newProducts
        )
    }

    fun updateQuantityByProductId(
        productId: Long,
        delta: Int
    ): ProductListUiState {
        val updated = productViewTypes.map { productViewType ->
            if (productViewType is ProductListViewType.ProductItemType && productViewType.product.id == productId) {
                ProductListViewType.ProductItemType(
                    productViewType.product, productViewType.quantity + delta
                )
            } else {
                productViewType
            }
        }
        return ProductListUiState(this.pageNumber, updated)
    }

    fun getQuantityByProductId(productId: Long): Int {
        val viewType = productViewTypes.find { productViewType ->
            productViewType is ProductListViewType.ProductItemType && productViewType.product.id == productId
        } ?: return 0
        return (viewType as ProductListViewType.ProductItemType).quantity
    }

}
