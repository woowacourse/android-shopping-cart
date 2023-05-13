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

    private var page = Counter(1)

    init {
        initCart()
        setView()
    }

    override fun initCart() {
        val cartProducts = getCurrentPageProducts(page.value)
        view.initCartItems(cartProducts.toPresentation())
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
    }

    override fun plusPage() {
        page = page.plus(1)
        view.setPage(page.value)
    }

    override fun minusPage() {
        page = page.minus(1)
        view.setPage(page.value)
    }

    override fun updateCart() {
        val cartProducts = getCurrentPageProducts(page.value)
        view.setCartItems(cartProducts.toPresentation())
    }
    override fun updateRightPageState() {
        if (getCurrentPageProducts(page.value + START_PAGE).items.isEmpty()) {
            view.setRightPageState(false)
        } else {
            view.setRightPageState(true)
        }
    }

    override fun updateLeftPageState() {
        if (page.value == START_PAGE) {
            view.setLeftPageState(false)
        } else {
            view.setLeftPageState(true)
        }
    }

    private fun setView() {
        view.setPage(page.value)
        updateCart()
        updateRightPageState()
        updateLeftPageState()
    }

    private fun getCurrentPageProducts(page: Int): Products {
        val cartProductIds = cartRepository.getCartProductIds(
            limit = PAGE_PER_PRODUCT_COUNT,
            offset = (page - START_PAGE) * PAGE_PER_PRODUCT_COUNT,
        )
        return Products(
            cartProductIds.map { productRepository.findProductById(it) ?: Product.defaultProduct },
        )
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val START_PAGE = 1
        private const val PAGE_PER_PRODUCT_COUNT = 5
    }
}
