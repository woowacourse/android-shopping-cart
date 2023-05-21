package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.pagination.ProductListPagination
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {
    private val productListPagination = ProductListPagination(PAGINATION_SIZE, productRepository)

    private val productsListItems = mutableListOf<ProductListViewItem>()

    override fun fetchProducts() {
        // 최근 본 항목
        val viewedItems = recentViewedRepository.findAll().reversed()
        if (viewedItems.isNotEmpty()) addViewedProductsItem(viewedItems)
        // 상품 리스트
        val currentProducts = productListPagination.nextItems()
        addProductsItem(convertProductsToModels(currentProducts))
        // 더보기
        if (productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())
        view.showProducts(productsListItems)
    }

    private fun addViewedProductsItem(ids: List<Int>) {
        val viewedProductsModel = ids.map { convertIdToProductModel(it) }
        productsListItems.add(ProductListViewItem.RecentViewedItem(viewedProductsModel))
    }

    private fun addProductsItem(products: List<ProductModel>) {
        productsListItems.addAll(products.map { ProductListViewItem.ProductItem(it) })
    }

    override fun showProductDetail(product: ProductModel) {
        val recentViewedItem =
            productsListItems.filterIsInstance<ProductListViewItem.RecentViewedItem>().getOrNull(0)
        var lastViewedProduct: ProductModel? = null
        if (recentViewedItem != null) {
            lastViewedProduct = recentViewedItem.products[0]
        }
        view.onClickProductDetail(product, lastViewedProduct)
    }

    override fun loadMoreProducts() {
        val recentViewedItemSize =
            productsListItems.filterIsInstance<ProductListViewItem.RecentViewedItem>().size
        val productsItemSize =
            productsListItems.filterIsInstance<ProductListViewItem.ProductItem>().size

        val mark = productsItemSize + recentViewedItemSize
        val nextProducts = convertProductsToModels(productListPagination.nextItems())

        // RecyclerView Items 수정
        productsListItems.removeLast()
        addProductsItem(nextProducts)
        if (productListPagination.isNextEnabled) productsListItems.add(ProductListViewItem.ShowMoreItem())

        // Notify
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    override fun addToCartProducts(id: Int, count: Int) {
        cartRepository.add(id, count)
        fetchProductCount(id)
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        if (count == 0) {
            cartRepository.remove(id)
            fetchProductCount(id)
            fetchCartCount()
            return
        }
        cartRepository.update(id, count)
        fetchProductCount(id)
    }

    override fun fetchCartCount() {
        view.showCartCount(cartRepository.findAll().size)
    }

    override fun fetchProductCounts() {
        val cartProducts = cartRepository.findAll()

        val itemsHaveCount = productsListItems
            .asSequence()
            .filterIsInstance<ProductListViewItem.ProductItem>()
            .filter { it.product.count > 0 }
            .toList()

        itemsHaveCount.forEach { item ->
            val cartProduct = cartProducts.find { it.id == item.product.id }
            item.product.count = cartProduct?.count ?: 0
            view.notifyDataChanged(productsListItems.indexOf(item))
        }
    }

    override fun fetchProductCount(id: Int) {
        if (id == -1) return
        val product = cartRepository.find(id)
        val item = productsListItems.filterIsInstance<ProductListViewItem.ProductItem>()
            .filter { it.product.id == id }[0]
        item.product.count = product?.count ?: 0
        view.notifyDataChanged(productsListItems.indexOf(item))
    }

    override fun updateRecentViewed(id: Int) {
        if (id == -1) return
        if (isExistRecentViewed()) productsListItems.removeIf { it is ProductListViewItem.RecentViewedItem }

        val viewedProductModels = convertIdsToProductModels(recentViewedRepository.findAll()).reversed()
        productsListItems.add(0, ProductListViewItem.RecentViewedItem(viewedProductModels))
        view.notifyRecentViewedChanged()
    }

    private fun convertIdToProductModel(id: Int) =
        productRepository.find(id).toUiModel(cartRepository.find(id)?.count ?: 0)

    private fun convertIdsToProductModels(ids: List<Int>) = ids.map { convertIdToProductModel(it) }

    private fun convertProductToModel(product: Product) =
        product.toUiModel(cartRepository.find(product.id)?.count ?: 0)

    private fun convertProductsToModels(products: List<Product>) =
        products.map { convertProductToModel(it) }

    private fun isExistRecentViewed(): Boolean =
        productsListItems[0] is ProductListViewItem.RecentViewedItem

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
