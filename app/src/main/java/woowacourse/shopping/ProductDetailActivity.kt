package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

        val selectedProductId =
            intent.getStringExtra(IntentKeys.SELECTED_PRODUCT_ID_KEY) ?: run {
                finish()
                return
            }

        val lastViewedProductId =
            intent.getStringExtra(IntentKeys.LATEST_VIEWED_PRODUCT_ID_KEY)

        enableEdgeToEdge()
        setContent {
            val viewModel: ProductDetailViewModel =
                viewModel<ProductDetailViewModel>(
                    factory =
                        ProductDetailViewModelFactory(
                            purchaseProductsRepository = (application as ShoppingApplication).purchaseProductsRepository,
                            recentlyViewedProductRepository = (application as ShoppingApplication).recentlyViewedProductRepository,
                            productRepository = (application as ShoppingApplication).productRepository,
                            selectedProductId = selectedProductId,
                            lastViewedProductId = lastViewedProductId,
                        ),
                )

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val count = viewModel.countState.collectAsStateWithLifecycle()
                    val selectedProduct = viewModel.selectedProduct.collectAsStateWithLifecycle()
                    val lastViewedProduct = viewModel.lastViewedProduct.collectAsStateWithLifecycle()
                    selectedProduct.value?.let {
                        ProductDetailScreen(
                            product = it,
                            count = count.value,
                            lastViewedProduct = lastViewedProduct.value,
                            onLastViewedClick = {
                                val intent = Intent(this, ProductDetailActivity::class.java)
                                lastViewedProduct.value?.run {
                                    viewModel.updateHistory(it)
                                    intent.putExtra(IntentKeys.SELECTED_PRODUCT_ID_KEY, it.id)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                } ?: finish()
                            },
                            onAdd = { viewModel.addCount() },
                            onMinus = { viewModel.minusCount() },
                            onAddRequest = {
                                viewModel.addPurchaseProduct(PurchaseProduct(it, count.value))
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
}
