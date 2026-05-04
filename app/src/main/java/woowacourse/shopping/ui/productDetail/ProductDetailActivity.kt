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
import woowacourse.shopping.repository.cart.MockCartRepository
import woowacourse.shopping.repository.product.MockProductRepository
import woowacourse.shopping.ui.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)
        if (productId == null) {
            finish()
            return
        }

        setContent {
            val viewModel: ProductDetailViewModel =
                viewModel(
                    factory =
                        ProductDetailViewModel.factory(
                            productId = productId,
                            productRepository = MockProductRepository(),
                            cartRepository = MockCartRepository,
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
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "PRODUCT_ID"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
