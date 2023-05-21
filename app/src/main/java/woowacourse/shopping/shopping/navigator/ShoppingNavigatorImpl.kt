package woowacourse.shopping.shopping.navigator

import android.content.Context
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.CartActivity

class ShoppingNavigatorImpl(private val context: Context) : ShoppingNavigator {

    override fun navigateToProductDetailView(
        product: ProductUiModel,
        latestViewedProduct: ProductUiModel?,
    ) {
        val intent = ProductDetailActivity.getIntent(
            context,
            product,
            latestViewedProduct
        )
        context.startActivity(intent)
    }

    override fun navigateToCartView() {
        val intent = CartActivity.getIntent(context)
        context.startActivity(intent)
    }
}
