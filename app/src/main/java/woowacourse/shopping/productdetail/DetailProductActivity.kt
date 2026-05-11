package woowacourse.shopping.productdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.R
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productId = intent.getStringExtra(ProductListActivity.EXTRA_PRODUCT_ID)
                if (productId == null) {
                    Text(stringResource(R.string.product_not_found_message))
                    return@AndroidShoppingTheme
                }
                DetailProductScreen(
                    productId = productId,
                    onNavigateToLastViewedProduct = { lastViewedProductId ->
                        startActivity(
                            Intent(this, DetailProductActivity::class.java)
                                .putExtra(ProductListActivity.EXTRA_PRODUCT_ID, lastViewedProductId)
                                .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        )
                        this.finish()
                    },
                    onBackClick = {
                        setResult(RESULT_OK)
                        this.finish()
                    },
                )
            }
        }
    }
}
