package woowacourse.shopping.feature.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.core.designsystem.theme.AndroidshoppingTheme
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.productDetail.ProductDetailActivity

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainProductsStateHolder()

                    ProductsScreen(
                        products = stateHolder.products,
                        isLastPage = stateHolder.isLastPage,
                        onCartClick = { startActivity(CartActivity.newIntent(this)) },
                        onProductClick = { startActivity(ProductDetailActivity.newIntent(this, it)) },
                        onLoadClick = stateHolder::getProducts,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
