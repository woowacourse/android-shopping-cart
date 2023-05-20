package woowacourse.shopping.productdetail

import model.CartProduct
import model.Count
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toDomainModel

class ProductDetailPresenter(
    product: ProductUiModel,
    private val view: ProductDetailContract.View,
    private val latestViewedProduct: ProductUiModel?,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    private var cartProduct: CartProduct = CartProduct(
        product = product.toDomainModel(),
        count = Count()
    )

    init {
        view.setUpProductDetailView(product = product)
        view.setUpLatestViewedProductView(latestViewedProduct)
    }

    override fun addToCart() {
        repository.insertToShoppingCart(
            id = cartProduct.product.id,
            count = cartProduct.count.value
        )
        view.navigateToCartView()
    }

    override fun loadLatestViewedProduct() {
        latestViewedProduct?.let {
            view.navigateToProductDetailView(it)
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
