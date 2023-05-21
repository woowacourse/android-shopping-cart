package woowacourse.shopping.productdetail.navigator

import android.content.Context
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
        )

        context.startActivity(intent)
    }
}
