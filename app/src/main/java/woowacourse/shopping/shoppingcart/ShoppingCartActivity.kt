package woowacourse.shopping.shoppingcart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ShoppingCartActivity : ComponentActivity() {
    private val changedProductIds = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                ShoppingCartScreen(
                    onBackClick = {
                        setResult(
                            RESULT_OK,
                            Intent().putStringArrayListExtra(
                                ProductListActivity.CHANGED_PRODUCT_IDS,
                                changedProductIds,
                            ),
                        )
                        this.finish()
                    },
                    onProductChanged = { productId ->
                        changedProductIds.add(productId)
                    },
                )
            }
        }
    }
}
