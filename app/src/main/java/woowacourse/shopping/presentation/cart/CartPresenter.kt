package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Counter
import woowacourse.shopping.Product
import woowacourse.shopping.Products
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : CartContract.Presenter {

    private var page = Counter(FIRST_PAGE)
    private val products: Products = Products()
    override fun initCart() {
        loadProducts()
        setCartPage()
    }

    private fun checkPageAble() {
        checkRightPageAble()
        checkLeftPageAble()
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
        products.deleteProduct(productModel.id)
        setCartPage()
    }

    override fun plusPage() {
        page = page.plus(PAGE_UNIT)
        setCartPage()
    }

    override fun minusPage() {
        page = page.minus(PAGE_UNIT)
        setCartPage()
    }

    private fun setCartPage() {
        view.setPage(page.value)
        updateCart()
        checkPageAble()
    }

    private fun loadProducts() {
        val recentProductIds = cartRepository.getCartProductIds()
        val productItems = recentProductIds.map {
            productRepository.findProductById(it) ?: Product.defaultProduct
        }
        products.addProducts(productItems)
    }

    private fun updateCart() {
        val cartProducts = products.getProductsInRange(
            (page.value - FIRST_PAGE) * PRODUCT_CART_SIZE,
            PRODUCT_CART_SIZE,
        )
        view.setCartProductModels(cartProducts.toPresentation())
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    private fun checkRightPageAble() {
        if (((products.size - FIRST_PAGE) / PRODUCT_CART_SIZE + FIRST_PAGE) == page.value) {
            view.setRightPageEnable(false)
        } else {
            view.setRightPageEnable(true)
        }
    }

    private fun checkLeftPageAble() {
        if (page.value == FIRST_PAGE) {
            view.setLeftPageEnable(false)
        } else {
            view.setLeftPageEnable(true)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_UNIT = 1
        private const val PRODUCT_CART_SIZE = 5
    }
}
