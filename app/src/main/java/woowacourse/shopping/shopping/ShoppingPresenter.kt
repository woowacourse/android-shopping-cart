package woowacourse.shopping.shopping

import model.CartProduct
import model.RecentViewedProducts
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.product.repository.ProductRepository
import woowacourse.shopping.data.recentviewed.repository.RecentViewedProductRepository
import woowacourse.shopping.model.CartProductUiModel
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

    override fun setUpProducts() {
        val products = selectProducts()
        val recentViewedProducts = recentViewedProducts.values.map {
            it.toRecentViewedProductUiModel()
        }
        numberOfReadProduct += products.size

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
        )
        loadCartProductsCount()
    }

    override fun loadProducts() {
        view.refreshShoppingProductsView(
            products = selectProducts(0, numberOfReadProduct)
        )
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

        numberOfReadProduct += products.size

        view.showMoreProducts(products)
    }

    private fun selectProducts(
        from: Int = numberOfReadProduct,
        count: Int = COUNT_TO_READ,
    ): List<CartProductUiModel> {
        val products = productRepository.getProductInRange(
            from = from,
            count = count
        ).map { it.toProductUiModel() }

        return products.toCartProducts()
    }

    override fun addProductToShoppingCart(product: CartProductUiModel) {
        cartRepository.addToCart(id = product.id)

        view.refreshProductCount(
            count = cartRepository.getCountOfCartProducts()
        )
        view.refreshShoppingProductsView(
            products = selectProducts(0, numberOfReadProduct)
        )
    }

    override fun plusShoppingCartProductCount(product: CartProductUiModel) {
        val shoppingCartProduct = cartRepository.getCartProductById(product.id)
            .plusCount()

        cartRepository.addToCart(
            id = product.id,
            count = shoppingCartProduct.count.value
        )
        view.refreshShoppingProductsView(
            products = selectProducts(0, numberOfReadProduct)
        )
    }

    override fun minusShoppingCartProductCount(product: CartProductUiModel) {
        val cartProduct = cartRepository.getCartProductById(product.id)
            .minusCount(handleZeroCount = ::removeCartProduct)

        cartProduct?.let {
            cartRepository.addToCart(
                id = product.id,
                count = it.count
                    .value
            )
        }
        view.refreshShoppingProductsView(
            products = selectProducts(0, numberOfReadProduct)
        )
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

    private fun List<ProductUiModel>.toCartProducts(): List<CartProductUiModel> {
        val cartProducts = cartRepository.getCartProducts()

        return this.map { product ->
            val count = cartProducts.find { cartProduct ->
                product.id == cartProduct.product.id
            }?.count?.value ?: 0

            CartProductUiModel(
                id = product.id,
                name = product.name,
                imageUrl = product.imageUrl,
                price = product.price,
                count = count,
                selected = true
            )
        }
    }

    companion object {
        private const val COUNT_TO_READ = 20
    }
}
