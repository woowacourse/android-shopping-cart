package woowacourse.shopping.productdetail

import android.content.Context
import woowacourse.shopping.database.ShoppingDBRepository
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.model.ProductUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    override val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id)
        view.navigateToShoppingCartView()
    }

    companion object {
        fun of(
            view: ProductDetailContract.View,
            product: ProductUiModel,
            context: Context,
        ): ProductDetailPresenter {
            return ProductDetailPresenter(
                view,
                product,
                ShoppingDBRepository(ShoppingDao(context)),
            )
        }
    }
}
