package woowacourse.shopping.productdetail

import model.CartProduct
import model.Count
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toProductDomainModel

class ProductDetailPresenter(
    product: ProductUiModel,
    private val view: ProductDetailContract.View,
    private val latestViewedProduct: ProductUiModel?,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {

    private var cartProduct: CartProduct = CartProduct(
        product = product.toProductDomainModel(),
        count = Count()
    )

    init {
        view.setUpProductDetailView(product = product)
        view.setUpLatestViewedProductView(latestViewedProduct)
    }

    override fun addToCart() {
        cartRepository.addToCart(
            id = cartProduct.product.id,
            count = cartProduct.count.value
        )
        view.productDetailNavigator.navigateToCartView()
    }

    override fun loadLatestViewedProduct() {
        latestViewedProduct?.let {
            view.productDetailNavigator.navigateToProductDetailView(it)
        }
    }

    override fun plusCartProductCount() {
        cartProduct = cartProduct.plusCount()
        view.setUpDialogTotalPriceView(totalPrice = cartProduct.price.value)
    }

    override fun minusCartProductCount() {
        cartProduct.minusCount()?.let {
            cartProduct = it
            view.setUpDialogTotalPriceView(totalPrice = it.price.value)
        }
    }
}
