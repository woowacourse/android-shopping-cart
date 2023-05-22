package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.cart.CartEntity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartPages
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Counter
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val initialPage: Counter = Counter(CartPages.FIRST_PAGE),
) : CartContract.Presenter {

    private lateinit var cartPages: CartPages
    override fun loadCart() {
        initCartPages()
        updateProductsInCurrentPage()
    }

    private fun initCartPages() {
        val productItems = loadCartProducts()
        cartPages = CartPages(CartProducts(productItems), initialPage)
    }

    private fun loadCartProducts(): List<CartProduct> {
        val cartEntities = cartRepository.getCartEntities()
        val productItems = cartEntities.map { cart ->
            cartToProductItem(cart)
        }
        return productItems
    }

    private fun cartToProductItem(cart: CartEntity): CartProduct {
        val cartProduct =
            productRepository.findProductById(cart.productId) ?: Product.defaultProduct
        return CartProduct(cartProduct, cart.count, true)
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

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProduct(productModel.id)
        cartPages.deleteProducts(productModel.toDomain())
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
        view.setCheckBoxChecked(cartPages.isAllProductSelected())
        view.setTotalPrice(cartPages.getSelectedProductsPrice())
        view.setTotalCount(cartPages.getSelectedProductsCount())
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
        view.setPage(cartPages.pageNumber.value)
        view.setCartProductModels(cartProducts.toPresentation())
    }

    private fun checkRightPageAble() {
        view.setRightPageEnable(cartPages.isNextPageAble())
    }

    private fun checkLeftPageAble() {
        view.setLeftPageEnable(cartPages.isPreviousPageAble())
    }

    private fun CartProducts.toPresentation(): List<CartProductModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val CART_UNIT = 1
    }
}
