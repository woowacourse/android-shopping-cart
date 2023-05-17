package woowacourse.shopping.productdetail

import model.Count
import model.ShoppingCartProduct
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    private val latestViewedProduct: ProductUiModel? = repository.selectLatestViewedProduct()
        ?.toUiModel()
    private var shoppingCartProduct: ShoppingCartProduct = ShoppingCartProduct(
        product = product.toDomainModel(),
        count = Count()
    )

    init {
        view.setUpProductDetailView(
            product = product,
            navigateToLatestViewedProductView = ::loadLatestViewedProduct,
        )
        latestViewedProduct?.let {
            view.setUpLatestViewedProductView(it)
        }
    }

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(
            id = shoppingCartProduct.product.id,
            count = shoppingCartProduct.count.value
        )
        view.navigateToShoppingCartView()
    }

    override fun loadLatestViewedProduct() {
        latestViewedProduct?.let {
            view.navigateToProductDetailView(it)
        }
    }

    override fun plusShoppingCartProductCount(currentCount: Int) {
        val count = currentCount.toDomainModel().plus()

        shoppingCartProduct = ShoppingCartProduct(
            product = product.toDomainModel(),
            count = count
        )
        view.setUpDialogProductCountView(count.value)
        view.setUpDialogTotalPriceView(shoppingCartProduct.price.value)
    }

    override fun minusShoppingCartProductCount(currentCount: Int) {
        val count = currentCount.toDomainModel().minus()

        shoppingCartProduct = ShoppingCartProduct(
            product = product.toDomainModel(),
            count = count
        )
        view.setUpDialogProductCountView(count.value)
        view.setUpDialogTotalPriceView(shoppingCartProduct.price.value)
    }

    private fun Int.toDomainModel() = Count(this)
}
