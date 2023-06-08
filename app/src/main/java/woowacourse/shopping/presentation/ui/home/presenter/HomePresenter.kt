package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState
import kotlin.concurrent.thread

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : HomeContract.Presenter {
    private var productsCount = 0
    override fun fetchAllProductsOnHome() {
        val recentlyViewedProducts =
            productRepository.getRecentlyViewedProducts(10).toRecentProductsByView()
        val productsFromRemote = getProductsFromRemote(0).toProductsByView()
        val showMoreButton = ShowMoreProducts
        val shoppingCart = shoppingCartRepository.getShoppingCart().map {
            it.toUiState()
        }
        val productsByView =
            getProductsByViews(recentlyViewedProducts, productsFromRemote, showMoreButton)

        productsCount = productsFromRemote.size
        view.setUpProductsOnHome(productsByView, shoppingCart)
    }

    private fun getProductsFromRemote(lastIndex: Int): List<Product> {
        var result: List<Product> = emptyList()

        thread {
            result = when (val state = productRepository.getProductsFromRemote(20, lastIndex)) {
                is SUCCESS -> state.data
                is FAIL -> emptyList()
            }
        }.join()

        return result
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
        val products =
            getProductsFromRemote(productsCount).toProductsByView()

        productsCount += products.size
        view.setUpMoreProducts(products)
    }

    override fun addCountOfProductInCart(request: Operator, productInCart: Product) {
        val quantity = Quantity()
        when (request) {
            Operator.PLUS -> quantity.add()
            Operator.MINUS -> quantity.subtract()
        }

        shoppingCartRepository.addProductInCart(
            ProductInCart(
                productInCart,
                quantity.amount,
                false,
            ),
        )

        val shoppingCart = shoppingCartRepository.getShoppingCart().map { it.toUiState() }

        view.setUpCountOfProductInCart(shoppingCart)
    }

    private fun List<Product>.toProductsByView(): List<Products> {
        return this.map { product ->
            Products(product)
        }
    }

    private fun List<Product>.toRecentProductsByView(): RecentlyViewedProducts =
        RecentlyViewedProducts(this)

    private fun ProductInCart.toUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this.product,
        quantity = this.quantity,
        isChecked = false,
    )
}
