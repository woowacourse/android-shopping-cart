package woowacourse.shopping.shopping

import model.RecentViewedProducts
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository,
) : ShoppingContract.Presenter {

    override val recentViewedProducts = RecentViewedProducts(
        products = repository.selectRecentViewedProducts()
    )
    private var numberOfReadProduct: Int = 0

    init {
        repository.setUpDB()
    }

    override fun loadProducts() {
        val products = selectProducts()
        val recentViewedProducts = recentViewedProducts.values.map { it.toUiModel() }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
        )
        loadCartProductsCount()
    }

    override fun loadCartProductsCount() {
        view.refreshProductCount(
            count = repository.getCountOfShoppingCartProducts()
        )
    }

    override fun loadProductDetail(product: ProductUiModel) {
        view.navigateToProductDetailView(
            product = product,
            latestViewedProduct = repository.selectLatestViewedProduct()?.toUiModel()
        )
    }

    override fun readMoreShoppingProducts() {
        val products = selectProducts()

        view.refreshShoppingProductsView(products)
    }

    override fun addProductToShoppingCart(product: ProductUiModel) {
        repository.insertToShoppingCart(id = product.id)

        view.refreshProductCount(
            count = repository.getCountOfShoppingCartProducts()
        )
    }

    override fun plusShoppingCartProductCount(product: ProductUiModel) {
        val shoppingCartProduct = repository.selectShoppingCartProductById(product.id)
            .plusCount()

        repository.insertToShoppingCart(
            id = product.id,
            count = shoppingCartProduct.count.value
        )
    }

    override fun minusShoppingCartProductCount(product: ProductUiModel) {
        val shoppingCartProduct = repository.selectShoppingCartProductById(product.id)

        if (shoppingCartProduct.count.value == 1) {
            repository.deleteFromShoppingCart(product.id)
            view.refreshProductCount(
                count = repository.getCountOfShoppingCartProducts()
            )
        }

        repository.insertToShoppingCart(
            id = product.id,
            count = shoppingCartProduct.minusCount()
                .count
                .value
        )
    }

    private fun selectProducts(): List<ProductUiModel> {
        val products = repository.selectProducts(
            from = numberOfReadProduct,
            count = COUNT_TO_READ
        ).map { it.toUiModel() }

        numberOfReadProduct += products.size

        return products
    }

    override fun addToRecentViewedProduct(id: Int) {
        val product = repository.selectRecentViewedProductById(id)
        val removedProduct = recentViewedProducts.add(product)

        removedProduct?.let {
            repository.deleteFromRecentViewedProducts()
        }
        repository.insertToRecentViewedProducts(id)
        view.refreshRecentViewedProductsView(
            products = recentViewedProducts.values.map { it.toUiModel() }
        )
    }

    companion object {
        private const val COUNT_TO_READ = 20
    }
}
