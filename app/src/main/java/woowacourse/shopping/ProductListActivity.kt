package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import woowacourse.shopping.ui.shopping.viewmodel.ProductListViewModel
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel =
            ProductListViewModel(
                AppContainer.createProductRepository(packageName),
                AppContainer.cartRepository,
            )

        setContent {
            AndroidShoppingTheme {
                ProductListScreen(
                    viewModel = viewModel,
                    products = Products(ProductFixture.productList(packageName)),
                    onCartClick = { startActivity(Intent(this, CartActivity::class.java)) },
                    onProductClick = { productId ->
                        ProductDetailActivity.start(this, productId)
                    },
                    modifier = Modifier.testTag("product_list"),
                )
            }
        }
    }
}
