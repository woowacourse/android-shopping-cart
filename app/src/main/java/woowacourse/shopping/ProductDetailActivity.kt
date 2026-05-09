package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import woowacourse.shopping.ui.viewmodel.ProductDetailViewModel
import woowacourse.shopping.ui.viewmodel.ProductDetailViewModelFactory

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product =
            intent?.getParcelableExtra<Product>(IntentKeys.SELECTED_PRODUCT_KEY) ?: run {
                finish()
                return
            }

        enableEdgeToEdge()
        setContent {
            val viewModel: ProductDetailViewModel = viewModel<ProductDetailViewModel>(
                factory = ProductDetailViewModelFactory(
                    (application as ShoppingApplication).purchaseProductsRepository
                )
            )

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        count = viewModel.countState,
                        onAdd = { viewModel.addCount() },
                        onMinus = { viewModel.minusCount() },
                        onAddRequest = {
                            viewModel.addPurchaseProduct(PurchaseProduct(product, viewModel.countState))
                            finish()
                        },
                        onClose = { finish() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
