package woowacourse.shopping.view.productlist

import woowacourse.shopping.R
import woowacourse.shopping.data.server.ProductServiceImpl
import woowacourse.shopping.domain.CartProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductServiceImpl,
    private val recentViewedRepository: RecentViewedRepository,
    private val cartProductRepository: CartProductRepository,
) : ProductListContract.Presenter {
    private val productListPagination = ProductListPagination(PAGINATION_SIZE, productRepository)
    private val products = productListPagination.nextItems().map { it.toUiModel() }.toMutableList()

    override fun fetchProducts() {
        val viewedProducts = recentViewedRepository.findAll()
        productRepository.get()
        val viewedProductsUiModel =
            viewedProducts.map { productRepository.find(it).toUiModel() }.reversed()

        setCartProductCount()
        view.showProducts(viewedProductsUiModel, products)
    }

    override fun showMoreProducts() {
        val viewedProducts = recentViewedRepository.findAll()
        val mark = if (viewedProducts.isNotEmpty()) products.size + 1 else products.size
        products.addAll(productListPagination.nextItems().map { it.toUiModel() })

        setCartProductCount()
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    override fun calculateSpanSize(recentViewedProducts: List<ProductModel>, position: Int): Int {
        val isHeader = recentViewedProducts.isNotEmpty() && position == 0
        val isFooter =
            if (recentViewedProducts.isNotEmpty()) position == products.size + 1 else position == products.size
        return if (isHeader || isFooter) {
            2
        } else {
            1
        }
    }

    private fun setCartProductCount() {
        val cartProducts = cartProductRepository.findAll()
        products.map { product ->
            val count = cartProducts.find { cartProduct -> cartProduct.id == product.id }?.count ?: 0
            product.count = count
        }
    }

    override fun getCartItemsCount(): Int {
        return cartProductRepository.findAll().sumOf { it.count }
    }

    override fun addProductCount(product: ProductModel) {
        cartProductRepository.add(product.id, 1, true)
        product.count++
    }

    override fun plusProductCount(product: ProductModel) {
        cartProductRepository.plusCount(product.id)
        product.count++
    }

    override fun minusProductCount(product: ProductModel) {
        if (product.count <= 1) {
            cartProductRepository.remove(product.id)
            product.count--
        } else {
            cartProductRepository.subCount(product.id)
            product.count--
        }
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            R.id.cart -> {
                view.handleCartMenuClicked()
            }
        }
    }

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
