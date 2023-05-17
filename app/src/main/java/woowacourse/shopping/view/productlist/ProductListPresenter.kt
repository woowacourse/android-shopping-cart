package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {
    private val productListPagination = ProductListPagination(PAGINATION_SIZE, productRepository)
    private val products = productListPagination.nextItems()
        .map { it.toUiModel(cartRepository.find(it.id)?.count ?: 0) }.toMutableList()

    private val productsListItems = mutableListOf<ProductListViewItem>()
    override fun fetchProducts() {
        // 상품 리스트
        productsListItems.addAll(products.map { ProductListViewItem.ProductItem(it) })
        // 더보기
        if (productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())
        view.showProducts(productsListItems)
    }

    override fun showMoreProducts() {
        val mark = products.size
        val nextProducts = productListPagination.nextItems().map { it.toUiModel() }
        products.addAll(nextProducts)
        // RecyclerView Items 수정
        productsListItems.removeLast()
        productsListItems.addAll(nextProducts.map { ProductListViewItem.ProductItem(it) })
        if (productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())
        // Notify
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    override fun addToCartProducts(id: Int, count: Int) {
        cartRepository.add(id, count)
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        cartRepository.update(id, count)
    }

    override fun fetchCartCount() {
        view.showCartCount(cartRepository.findAll().size)
    }


    override fun updateRecentViewed(id: Int) {
        if (id == -1) return
        view.notifyRecentViewedChanged()
    }

    private fun convertIdToProductModel(id: Int) = productRepository.find(id).toUiModel()

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
