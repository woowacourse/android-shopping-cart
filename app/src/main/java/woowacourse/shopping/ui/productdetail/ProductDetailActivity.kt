package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
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
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    private val viewModel: ProductDetailViewModel by viewModels()

    companion object {
        private const val PUT_EXTRA_KEY_PRODUCT_ID = "PRODUCT_ID"

        fun startActivity(
            context: Context,
            productId: ProductId,
        ) {
            val intent =
                Intent(context, ProductDetailActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra(PUT_EXTRA_KEY_PRODUCT_ID, productId.value.toString())
                }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedProductId = parseProductId(intent)
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
                        lastViewedProduct = uiState.lastViewedProduct,
                        quantity = uiState.quantity,
                        isAdding = uiState.isAdding,
                        isNetworkConnected = uiState.isNetworkConnected,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCart = viewModel::addToCart,
                        onLastViewedProductClick = {
                            ProductDetailActivity.startActivity(this, it.id)
                        },
                        onIncreaseQuantity = viewModel::increaseQuantity,
                        onDecreaseQuantity = viewModel::decreaseQuantity,
                    )
                }
            }
        }
    }

    private fun parseProductId(intent: Intent): ProductId? =
        runCatching {
            intent
                .getStringExtra(PUT_EXTRA_KEY_PRODUCT_ID)
                ?.let { ProductId(UUID.fromString(it)) }
        }.getOrNull()
}
