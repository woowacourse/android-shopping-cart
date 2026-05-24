package woowacourse.shopping.ui.product_detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.product_detail.component.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product = intent.getProductExtra()
        if (product == null) {
            finish()
            return
        }

        val app = application as ShoppingApplication
        val showLastViewedProduct = intent.getBooleanExtra(EXTRA_SHOW_LAST_VIEWED_PRODUCT, true)
        val toast = Toast.makeText(this, "장바구니에 담았습니다", Toast.LENGTH_SHORT)

        enableEdgeToEdge()
        setContent {
            val viewModel: ProductDetailViewModel = viewModel(
                factory = ProductDetailViewModel.provideFactory(
                    product,
                    app.cartRepository,
                    app.recentProductRepository,
                    showLastViewedProduct,
                )
            )
            AndroidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        amount = uiState.amount,
                        lastViewedProduct = uiState.lastViewedProduct,
                        onAddRequest = {
                            viewModel.addProductToCart()
                            toast.show()
                            finish()
                        },
                        onClose = { finish() },
                        onIncrease = { viewModel.onIncrease() },
                        onDecrease = { viewModel.onDecrease() },
                        onLastViewedProductClick = { lastViewedProduct ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(EXTRA_PRODUCT, lastViewedProduct)
                                putExtra(EXTRA_SHOW_LAST_VIEWED_PRODUCT, false)
                            }
                            startActivity(intent)
                            finish()
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    private fun Intent.getProductExtra(): Product? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtra(EXTRA_PRODUCT, Product::class.java)
        } else {
            @Suppress("DEPRECATION")
            getParcelableExtra(EXTRA_PRODUCT)
        }
    }

    companion object {
        const val EXTRA_PRODUCT = "product"
        const val EXTRA_SHOW_LAST_VIEWED_PRODUCT = "showLastViewedProduct"
    }
}
