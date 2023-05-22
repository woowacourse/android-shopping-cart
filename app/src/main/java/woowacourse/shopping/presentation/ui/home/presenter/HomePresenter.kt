package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : HomeContract.Presenter {
    private var productsCount = 0
    override fun fetchAllProductsOnHome() {
        val recentlyViewedProducts =
            productRepository.getRecentlyViewedProducts(10).toRecentProductsByView()
        val products = productRepository.getProducts(20, 0).toProductsByView()
        val showMoreButton = ShowMoreProducts
        val shoppingCart = shoppingCartRepository.getShoppingCart().map {
            it.toUiState()
        }
        val productsByView = getProductsByViews(recentlyViewedProducts, products, showMoreButton)

        productsCount = products.size
        view.setUpProductsOnHome(productsByView, shoppingCart)
    }

    private fun getProductsByViews(
        recentlyViewedProducts: RecentlyViewedProducts,
        products: List<Products>,
        showMoreButton: ShowMoreProducts,
    ) = if (recentlyViewedProducts.recentProduct.isEmpty()) {
        products + showMoreButton
    } else {
        listOf(recentlyViewedProducts) + products + showMoreButton
    }

    override fun fetchMoreProducts() {
        val products = productRepository.getProducts(20, productsCount).toProductsByView()

        productsCount += products.size
        view.setUpMoreProducts(products)
    }

    override fun addCountOfProductInCart(request: Operator, productInCart: Product) {
        val quantity = Quantity()
        when (request) {
            Operator.PLUS -> quantity.add()
            Operator.MINUS -> quantity.subtract()
        }

        shoppingCartRepository.addProductInCart(ProductInCart(productInCart, quantity.amount))

        val shoppingCart = shoppingCartRepository.getShoppingCart().map { it.toUiState() }

        view.setUpCountOfProductInCart(shoppingCart)
    }

    private fun List<Product>.toProductsByView(): List<Products> = this.map { product ->
        Products(product)
    }

    private fun List<Product>.toRecentProductsByView(): RecentlyViewedProducts =
        RecentlyViewedProducts(this)

    private fun ProductInCart.toUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this.product,
        quantity = this.quantity,
        isChecked = false
    )
}
