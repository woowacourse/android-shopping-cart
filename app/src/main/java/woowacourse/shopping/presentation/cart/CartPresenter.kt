package woowacourse.shopping.presentation.cart

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
    private val initialPage: Counter = Counter(0),
) : CartContract.Presenter {

    private lateinit var cartPages: CartPages
    override fun loadCart() {
        initCartPages()
        loadFirstCartPage()
    }

    private fun initCartPages() {
        val productItems = loadCartProducts()
        cartPages = CartPages(CartProducts(productItems), initialPage)
    }

    private fun loadCartProducts(): List<CartProduct> {
        val cartEntities = cartRepository.getCartEntities()
        val productItems = cartEntities.map { cart ->
            val cartProduct =
                productRepository.findProductById(cart.productId) ?: Product.defaultProduct
            CartProduct(cartProduct, cart.count, true)
        }
        return productItems
    }

    private fun loadFirstCartPage() {
        val nextProducts = cartPages.getNextPageProducts()
        updateCart(nextProducts)
        updateEvent()
    }

    private fun checkPageAble() {
        checkRightPageAble()
        checkLeftPageAble()
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProduct(productModel.id)
        val deletedProducts = cartPages.getDeletedProducts(productModel.toDomain())
        if (deletedProducts.size == 0) {
            minusPage()
            return
        }
        updateCart(deletedProducts)
        updateEvent()
    }

    override fun addProductCartCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count + CART_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        val addCountProducts =
            cartPages.getAddCountProducts(cartProductModel.productModel.toDomain())
        updateCart(addCountProducts)
        updateEvent()
    }

    override fun subProductCartCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count - CART_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        val subCountProducts =
            cartPages.getSubCountProducts(cartProductModel.productModel.toDomain())
        if (subCountProducts.size == 0) {
            minusPage()
            return
        }
        updateCart(subCountProducts)
        updateEvent()
    }

    override fun changeProductSelected(productModel: ProductModel) {
        cartPages.changeSelectedProduct(productModel.toDomain())
        setTotal()
    }

    private fun setTotal() {
        view.setCheckBoxChecked(cartPages.isAllProductSelected())
        view.setTotalPrice(cartPages.getSelectedProductsPrice())
        view.setTotalCount(cartPages.getSelectedProductsCount())
    }

    override fun plusPage() {
        val nextProducts = cartPages.getNextPageProducts()
        updateCart(nextProducts)
        updateEvent()
    }

    override fun minusPage() {
        val previousProducts = cartPages.getPreviousPageProducts()
        updateCart(previousProducts)
        updateEvent()
    }

    override fun selectAllProduct() {
        cartPages.selectPageProducts()
        updateCart(cartPages.getCurrentProducts())
        setTotal()
    }

    override fun unselectAllProduct() {
        cartPages.unselectPageProducts()
        updateCart(cartPages.getCurrentProducts())
        setTotal()
    }

    private fun updateEvent() {
        checkPageAble()
        setTotal()
    }

    private fun updateCart(cartProducts: CartProducts) {
        view.setPage(cartPages.pageNumber.value)
        view.setCartProductModels(cartProducts.toPresentation())
    }

    private fun CartProducts.toPresentation(): List<CartProductModel> {
        return items.map { it.toPresentation() }
    }

    private fun checkRightPageAble() {
        view.setRightPageEnable(cartPages.isNextPageAble())
    }

    private fun checkLeftPageAble() {
        view.setLeftPageEnable(cartPages.isPreviousPageAble())
    }

    companion object {
        private const val CART_UNIT = 1
    }
}
