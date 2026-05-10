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

        val product =
            intent?.getParcelableExtra<Product>(IntentKeys.SELECTED_PRODUCT_KEY) ?: run {
                finish()
                return
            }

        val lastViewedProduct = intent?.getParcelableExtra<Product>(IntentKeys.LATEST_VIEWED_PRODUCT)

        enableEdgeToEdge()
        setContent {
            val viewModel: ProductDetailViewModel =
                viewModel<ProductDetailViewModel>(
                    factory =
                        ProductDetailViewModelFactory(
                            (application as ShoppingApplication).purchaseProductsRepository,
                            (application as ShoppingApplication).recentlyViewedProductRepository,
                        ),
                )

            val count = viewModel.countState.collectAsStateWithLifecycle()

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        count = count.value,
                        lastViewedProduct = lastViewedProduct,
                        onLastViewedClick = {
                            viewModel.updateHistory(lastViewedProduct!!)
                            val intent =
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra(IntentKeys.SELECTED_PRODUCT_KEY, lastViewedProduct)
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                }
                            startActivity(intent)
                        },
                        onAdd = { viewModel.addCount() },
                        onMinus = { viewModel.minusCount() },
                        onAddRequest = {
                            viewModel.addPurchaseProduct(PurchaseProduct(product, count.value))
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
