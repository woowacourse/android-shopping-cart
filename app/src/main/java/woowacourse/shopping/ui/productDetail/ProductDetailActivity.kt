package woowacourse.shopping.ui.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as ShoppingApplication).appContainer
        val productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1)
        val openedFromLastViewed = intent.getBooleanExtra(EXTRA_OPENED_FROM_LAST_VIEWED, false)
        if (productId == -1) {
            finish()
            return
        }
        setContent {
            val viewModel: ProductDetailViewModel =
                viewModel(
                    factory =
                        ProductDetailViewModel.factory(
                            productId = productId,
                            openedFromLastViewed = openedFromLastViewed,
                            productRepository = appContainer.productRepository,
                            cartRepository = appContainer.cartRepository,
                            recentProductRepository = appContainer.recentProductRepository,
                        ),
                )
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    onAddToCartClick = {
                        viewModel.addToCart()
                        val cartIntent = Intent(this, CartActivity::class.java)
                        startActivity(cartIntent)
                    },
                    onLastViewedProductClick = { product ->
                        startActivity(
                            newIntent(
                                context = this,
                                productId = product.id,
                                openedFromLastViewed = true,
                            ),
                        )
                    },
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
        private const val EXTRA_OPENED_FROM_LAST_VIEWED = "OPENED_FROM_LAST_VIEWED"

        fun newIntent(
            context: Context,
            productId: Int,
            openedFromLastViewed: Boolean = false,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                putExtra(EXTRA_OPENED_FROM_LAST_VIEWED, openedFromLastViewed)
            }
    }
}
