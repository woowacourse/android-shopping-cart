package woowacourse.shopping.productdetail.navigator

import android.content.Context
import android.content.Intent
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.CartActivity

class ProductDetailNavigatorImpl(private val context: Context) : ProductDetailNavigator {

    override fun navigateToCartView() {
        val intent = CartActivity.getIntent(context)

        context.startActivity(intent)
    }

    override fun navigateToProductDetailView(product: ProductUiModel) {
        val intent = ProductDetailActivity.getIntent(
            context = context,
            product = product,
            latestViewedProduct = null
        ).setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        )

        context.startActivity(intent)
    }
}
