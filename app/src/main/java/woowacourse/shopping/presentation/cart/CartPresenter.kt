package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartPages
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

    private lateinit var cartPages: CartPages
    override fun loadCart() {
        initCartPages()
        setCartProducts()
    }

    private fun initCartPages() {
        val productItems = loadCartProducts()
        cartPages = CartPages(Products(productItems))
    }

    private fun loadCartProducts(): List<Product> {
        val recentProductIds = cartRepository.getCartProductIds()
        val productItems = recentProductIds.map {
            productRepository.findProductById(it) ?: Product.defaultProduct
        }
        return productItems
    }

    private fun setCartProducts() {
        val nextProducts = cartPages.getNextPageProducts()
        updateCart(nextProducts)
        checkPageAble()
    }

    private fun checkPageAble() {
        checkRightPageAble()
        checkLeftPageAble()
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
        val deletedProducts = cartPages.getDeletedProducts(productModel.id)
        if (deletedProducts.size == 0) {
            minusPage()
            return
        }
        updateCart(deletedProducts)
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

    private fun updateCart(products: Products) {
        view.setPage(cartPages.pageNumber.value)
        view.setCartProductModels(products.toPresentation())
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    private fun checkRightPageAble() {
        view.setRightPageEnable(cartPages.isNextPageAble())
    }

    private fun checkLeftPageAble() {
        view.setLeftPageEnable(cartPages.isPreviousPageAble())
    }
}
