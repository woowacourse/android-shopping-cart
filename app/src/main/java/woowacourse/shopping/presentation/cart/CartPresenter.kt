package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.CartPages
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Counter
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val initialPage: Counter = Counter(CartPages.FIRST_PAGE),
) : CartContract.Presenter {

    private lateinit var cartPages: CartPages
    override fun loadCart() {
        initCartPages()
        updateProductsInCurrentPage()
    }

    private fun initCartPages() {
        val productItems =
            cartRepository.getCartProducts()
        cartPages = CartPages(CartProducts(productItems), initialPage)
    }

    private fun updateProductsInCurrentPage() {
        updateCart(cartPages.getCurrentProducts())
        checkPageAble()
        updateCartSelectedInfo()
    }

    private fun checkPageAble() {
        checkRightPageAble()
        checkLeftPageAble()
    }

    override fun deleteCartProduct(cartProductModel: CartProductModel) {
        cartRepository.deleteCartProduct(cartProductModel.cartId)
        cartPages.deleteProducts(cartProductModel.productModel.toDomain())
        val deletedProducts = cartPages.getCurrentProducts()
        if (deletedProducts.size == 0) {
            minusPage()
            return
        }
        updateProductsInCurrentPage()
    }

    override fun addProductCartCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count + CART_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        cartPages.addCountProducts(cartProductModel.productModel.toDomain())
        updateProductsInCurrentPage()
    }

    override fun subProductCartCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count - CART_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        cartPages.subCountProducts(cartProductModel.productModel.toDomain())
        updateProductsInCurrentPage()
    }

    override fun changeProductSelected(productModel: ProductModel) {
        cartPages.changeSelectedProduct(productModel.toDomain())
        updateCartSelectedInfo()
    }

    private fun updateCartSelectedInfo() {
        view.showAllCheckBoxIsChecked(cartPages.isAllProductSelected())
        view.showTotalPrice(cartPages.getSelectedProductsPrice())
        view.showTotalCount(cartPages.getSelectedProductsCount())
    }

    override fun plusPage() {
        cartPages.goNextPageProducts()
        updateProductsInCurrentPage()
    }

    override fun minusPage() {
        cartPages.goPreviousPageProducts()
        updateProductsInCurrentPage()
    }

    override fun selectAllProduct() {
        cartPages.selectPageProducts()
        updateProductsInCurrentPage()
    }

    override fun unselectAllProduct() {
        cartPages.unselectPageProducts()
        updateProductsInCurrentPage()
    }

    private fun updateCart(cartProducts: CartProducts) {
        view.showPageNumber(cartPages.pageNumber.value)
        view.showCartProductModels(cartProducts.toPresentation())
    }

    private fun checkRightPageAble() {
        view.showRightPageIsEnabled(cartPages.isNextPageAble())
    }

    private fun checkLeftPageAble() {
        view.showLeftPageIsEnabled(cartPages.isPreviousPageAble())
    }

    private fun CartProducts.toPresentation(): List<CartProductModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val CART_UNIT = 1
    }
}
