package woowacourse.shopping.productdetail

import model.CartProduct
import model.Count
import woowacourse.shopping.database.ShoppingCache
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toProductDomainModel

class ProductDetailPresenter(
    product: ProductUiModel,
    private val view: ProductDetailContract.View,
    private val latestViewedProduct: ProductUiModel?,
    private val shoppingCache: ShoppingCache,
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
        shoppingCache.insertToShoppingCart(
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
        cartProduct = cartProduct.minusCount()
        view.setUpDialogTotalPriceView(totalPrice = cartProduct.price.value)
    }
}
