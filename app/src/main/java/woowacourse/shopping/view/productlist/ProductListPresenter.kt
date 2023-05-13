package woowacourse.shopping.view.productlist

import android.util.Log
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductListPagination
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductListContract.Presenter {
    private val productListPagination = ProductListPagination(PAGINATION_SIZE, productRepository)
    private val products = productListPagination.nextItems().map { it.toUiModel() }.toMutableList()
    private val viewedProducts = recentViewedRepository.findAll().toMutableList()

    private val productsListItems = mutableListOf<ProductListViewItem>()
    override fun fetchProducts() {
        // 최근 본 항목
        val viewedProducts = recentViewedRepository.findAll().reversed()
        val viewedProductsItem =
            ProductListViewItem.RecentViewedItem(viewedProducts.map { convertIdToProductModel(it) })
        productsListItems.add(viewedProductsItem)
        // 상품 리스트
        productsListItems.addAll(products.map { ProductListViewItem.ProductItem(it) })
        // 더보기
        if(productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())
        view.showProducts(productsListItems)
    }

    override fun showMoreProducts() {
        val mark = if (isExistRecentViewed()) products.size + 1 else products.size
        val nextProducts = productListPagination.nextItems().map { it.toUiModel() }
        products.addAll(nextProducts)
        // RecyclerView Items 수정
        productsListItems.removeLast()
        productsListItems.addAll(nextProducts.map { ProductListViewItem.ProductItem(it) })
        if(productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())
        // Notify
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    override fun updateRecentViewed(id: Int) {
        if (id == -1) return
        viewedProducts.add(0, id)
        if (isExistRecentViewed()) productsListItems.removeAt(0)
        productsListItems.add(0, ProductListViewItem.RecentViewedItem(viewedProducts.map { convertIdToProductModel(it) }))
        view.notifyRecentViewedChanged()
    }

    private fun convertIdToProductModel(id: Int) =  productRepository.find(id).toUiModel()

    private fun isExistRecentViewed(): Boolean = productsListItems[0] is ProductListViewItem.RecentViewedItem

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
