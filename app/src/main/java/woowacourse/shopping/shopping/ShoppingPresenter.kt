package woowacourse.shopping.shopping

import model.CartProduct
import model.RecentViewedProducts
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.product.repository.ProductRepository
import woowacourse.shopping.data.recentviewed.repository.RecentViewedProductRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toProductUiModel
import woowacourse.shopping.util.toRecentViewedProductUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentViewedProductRepository: RecentViewedProductRepository,
) : ShoppingContract.Presenter {

    private val recentViewedProducts = RecentViewedProducts(
        products = recentViewedProductRepository.getRecentViewedProducts()
    )

    private var numberOfReadProduct: Int = 0

    override fun loadProducts() {
        val products = selectProducts()
        val recentViewedProducts = recentViewedProducts.values.map {
            it.toRecentViewedProductUiModel()
        }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
        )
        loadCartProductsCount()
    }

    override fun loadCartProductsCount() {
        view.refreshProductCount(
            count = cartRepository.getCountOfCartProducts()
        )
    }

    override fun loadProductDetail(productId: Int) {
        addToRecentViewedProduct(productId)
        view.shoppingNavigator.navigateToProductDetailView(
            product = productRepository.getProductById(productId).toProductUiModel(),
            latestViewedProduct = recentViewedProductRepository.getLatestViewedProduct()
                ?.toProductUiModel()
        )
    }

    override fun readMoreShoppingProducts() {
        val products = selectProducts()

        view.refreshShoppingProductsView(products)
    }

    private fun selectProducts(): List<ProductUiModel> {
        val products = productRepository.getProductInRange(
            from = numberOfReadProduct,
            count = COUNT_TO_READ
        ).map { it.toProductUiModel() }

        numberOfReadProduct += products.size

        return products
    }

    override fun addProductToShoppingCart(product: ProductUiModel) {
        cartRepository.addToCart(id = product.id)

        view.refreshProductCount(
            count = cartRepository.getCountOfCartProducts()
        )
    }

    override fun plusShoppingCartProductCount(product: ProductUiModel) {
        val shoppingCartProduct = cartRepository.getCartProductById(product.id)
            .plusCount()

        cartRepository.addToCart(
            id = product.id,
            count = shoppingCartProduct.count.value
        )
    }

    override fun minusShoppingCartProductCount(product: ProductUiModel) {
        val cartProduct = cartRepository.getCartProductById(product.id)
            .minusCount(handleZeroCount = ::removeCartProduct)

        cartProduct?.let {
            cartRepository.addToCart(
                id = product.id,
                count = it.count
                    .value
            )
        }
    }

    private fun removeCartProduct(cartProduct: CartProduct) {
        cartRepository.removeCartProductById(cartProduct.product.id)
        view.refreshProductCount(
            count = cartRepository.getCountOfCartProducts()
        )
    }

    override fun addToRecentViewedProduct(id: Int) {
        val product = recentViewedProductRepository.getRecentViewedProductById(id)

        recentViewedProducts.add(
            product = product,
            handleOldestViewedProduct = recentViewedProductRepository::removeRecentViewedProduct
        )
        recentViewedProductRepository.addToRecentViewedProduct(id)
        view.refreshRecentViewedProductsView(
            products = recentViewedProducts.values.map { it.toRecentViewedProductUiModel() }
        )
    }

    companion object {
        private const val COUNT_TO_READ = 20
    }
}
