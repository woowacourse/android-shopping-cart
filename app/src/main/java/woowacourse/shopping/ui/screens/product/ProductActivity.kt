package woowacourse.shopping.ui.screens.product

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import woowacourse.shopping.ui.screens.cart.CartActivity
import woowacourse.shopping.ui.screens.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductActivity : ComponentActivity() {
    val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidshoppingTheme {
                ProductScreen(
                    viewModel = viewModel,
                    onCartClick = {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    },
                    onProductClick = { id ->
                        val intent = ProductDetailActivity.getNewIntent(this, id)
                        startActivity(intent)
                    },
                )
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        viewModel.loadProducts()
    }
}
