package woowacourse.shopping.ui.productdetail

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.ui.theme.ShoppingTheme

class ProductDetailActivity : ComponentActivity() {
    private val viewModel: ProductDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProduct =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("PRODUCT", Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("PRODUCT")
            }

        if (receivedProduct == null) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(receivedProduct) {
                viewModel.setProduct(receivedProduct)
            }

            LaunchedEffect(uiState.isAdded) {
                if(uiState.isAdded) finish()
            }

            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val product = uiState.product ?: return@Scaffold

                    ProductDetailScreen(
                        product = product,
                        isAdding = uiState.isAdding,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCart = viewModel::addToCart,
                    )
                }
            }
        }
    }
}
