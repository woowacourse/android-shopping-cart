package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
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
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.ui.theme.ShoppingTheme

class ProductDetailActivity : ComponentActivity() {
    private val viewModel: ProductDetailViewModel by viewModels()

    companion object {
        private const val PUT_EXTRA_KEY_PRODUCT_ID = "PRODUCT_ID"

        fun startActivity(
            context: Context,
            productId: ProductId,
        ) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PUT_EXTRA_KEY_PRODUCT_ID, productId)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProductId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(PUT_EXTRA_KEY_PRODUCT_ID, ProductId::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(PUT_EXTRA_KEY_PRODUCT_ID)
            }

        if (receivedProductId == null) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(receivedProductId) {
                viewModel.loadProduct(receivedProductId)
            }

            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val product = uiState.product ?: return@Scaffold

                    ProductDetailScreen(
                        product = product,
                        quantity = uiState.quantity,
                        isAdding = uiState.isAdding,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCart = viewModel::addToCart,
                        onIncreaseQuantity = viewModel::increaseQuantity,
                        onDecreaseQuantity = viewModel::decreaseQuantity,
                    )
                }
            }
        }
    }
}
