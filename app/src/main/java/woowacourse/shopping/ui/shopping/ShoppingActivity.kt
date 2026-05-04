package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.theme.ShoppingTheme

class ShoppingActivity : ComponentActivity() {
    private val viewModel: ShoppingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingScreen(
                        products = uiState.products,
                        hasNext = uiState.hasNext,
                        isLoading = uiState.isLoading,
                        modifier = Modifier.padding(innerPadding),
                        onCartClick = {
                            startActivity(Intent(this, CartActivity::class.java))
                        },
                        onProductClick = {
                            val intent =
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra("PRODUCT", it)
                                }
                            startActivity(intent)
                        },
                        onMoreClick = viewModel::loadMore,
                    )
                }
            }
        }
    }
}
