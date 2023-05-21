package woowacourse.shopping.shopping

import model.CartProduct
import model.RecentViewedProducts
import woowacourse.shopping.database.ShoppingCache
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val shoppingCache: ShoppingCache,
) : ShoppingContract.Presenter {

    private val recentViewedProducts = RecentViewedProducts(
        products = shoppingCache.selectRecentViewedProducts()
    )
    private var numberOfReadProduct: Int = 0

    init {
        shoppingCache.setUpDB()
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
            count = shoppingCache.getCountOfShoppingCartProducts()
        )
    }

    override fun loadProductDetail(product: ProductUiModel) {
        view.navigateToProductDetailView(
            product = product,
            latestViewedProduct = shoppingCache.selectLatestViewedProduct()?.toUiModel()
        )
    }

    override fun readMoreShoppingProducts() {
        val products = selectProducts()

        view.refreshShoppingProductsView(products)
    }

    private fun selectProducts(): List<ProductUiModel> {
        val products = shoppingCache.selectProducts(
            from = numberOfReadProduct,
            count = COUNT_TO_READ
        ).map { it.toUiModel() }

        numberOfReadProduct += products.size

        return products
    }

    override fun addProductToShoppingCart(product: ProductUiModel) {
        shoppingCache.insertToShoppingCart(id = product.id)

        view.refreshProductCount(
            count = shoppingCache.getCountOfShoppingCartProducts()
        )
    }

    override fun plusShoppingCartProductCount(product: ProductUiModel) {
        val shoppingCartProduct = shoppingCache.selectShoppingCartProductById(product.id)
            .plusCount()

        shoppingCache.insertToShoppingCart(
            id = product.id,
            count = shoppingCartProduct.count.value
        )
    }

    override fun minusShoppingCartProductCount(product: ProductUiModel) {
        val shoppingCartProduct = shoppingCache.selectShoppingCartProductById(product.id)
            .minusCount(handleZeroCount = ::removeCartProduct)

        shoppingCache.insertToShoppingCart(
            id = product.id,
            count = shoppingCartProduct.minusCount()
                .count
                .value
        )
    }

    private fun removeCartProduct(cartProduct: CartProduct) {
        shoppingCache.deleteFromShoppingCart(cartProduct.product.id)
        view.refreshProductCount(
            count = shoppingCache.getCountOfShoppingCartProducts()
        )
    }

    override fun addToRecentViewedProduct(id: Int) {
        val product = shoppingCache.selectRecentViewedProductById(id)

        recentViewedProducts.add(
            product = product,
            handleOldestViewedProduct = shoppingCache::deleteFromRecentViewedProducts
        )
        shoppingCache.insertToRecentViewedProducts(id)
        view.refreshRecentViewedProductsView(
            products = recentViewedProducts.values.map { it.toUiModel() }
        )
    }

    companion object {
        private const val COUNT_TO_READ = 20
    }
}
