package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.productlist.ProductListRoute
import woowacourse.shopping.ui.productlist.stateholder.rememberProductListStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val productListStateHolder = rememberProductListStateHolder()

            val cartLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                if (result.resultCode != RESULT_OK) return@rememberLauncherForActivityResult
                val deletedCartItems = CartActivity.getDeletedList(result.data)
                    ?: return@rememberLauncherForActivityResult

                productListStateHolder.removeCartItems(deletedCartItems)
            }
            AndroidshoppingTheme {
                ProductListRoute(
                    onNavigateToDetail = { id ->
                        startActivity(ProductDetailActivity.newIntent(this, id))
                    },
                    onCartIconClick = {
                        cartLauncher.launch(CartActivity.newIntent(this, productListStateHolder.cartUiModels))
                    },
                )
            }
        }
    }
}
