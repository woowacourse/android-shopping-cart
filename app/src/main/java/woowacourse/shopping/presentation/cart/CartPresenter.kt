package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartPages
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : CartContract.Presenter {

    private lateinit var cartPages: CartPages
    override fun loadCart() {
        initCartPages()
        loadFirstCartPage()
    }

    private fun initCartPages() {
        val productItems = loadCartProducts()
        cartPages = CartPages(CartProducts(productItems))
    }

    private fun loadCartProducts(): List<CartProduct> {
        val cartEntities = cartRepository.getCartProducts()
        val productItems = cartEntities.map { cart ->
            val cartProduct =
                productRepository.findProductById(cart.productId) ?: Product.defaultProduct
            CartProduct(cartProduct, cart.count)
        }
        return productItems
    }

    private fun loadFirstCartPage() {
        val nextProducts = cartPages.getNextPageProducts()
        updateCart(nextProducts)
        checkPageAble()
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
        checkPageAble()
    }

    override fun addProductCount(productModel: ProductModel) {
        cartRepository.addCartProduct(productModel.id)
        val addCountProducts = cartPages.getAddCountProducts(productModel.toDomain())
        updateCart(addCountProducts)
        checkPageAble()
    }

    override fun subProductCount(productModel: ProductModel) {
        cartRepository.subProductCount(productModel.id)
        val subCountProducts = cartPages.getSubCountProducts(productModel.toDomain())
        if (subCountProducts.size == 0) {
            minusPage()
            return
        }
        updateCart(subCountProducts)
        checkPageAble()
    }

    override fun plusPage() {
        val nextProducts = cartPages.getNextPageProducts()
        updateCart(nextProducts)
        checkPageAble()
    }

    override fun minusPage() {
        val previousProducts = cartPages.getPreviousPageProducts()
        updateCart(previousProducts)
        checkPageAble()
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
}
