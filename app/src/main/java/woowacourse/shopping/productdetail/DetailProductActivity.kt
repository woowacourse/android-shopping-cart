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
                val hideLastViewedProduct =
                    intent.getBooleanExtra(EXTRA_HIDE_LAST_VIEWED_PRODUCT, false)

                DetailProductScreen(
                    productId = productId,
                    hideLastViewedProduct = hideLastViewedProduct,
                    onNavigateToLastViewedProduct = { lastViewedProductId ->
                        val intent =
                            Intent(this, DetailProductActivity::class.java)
                                .putExtra(ProductListActivity.EXTRA_PRODUCT_ID, lastViewedProductId)
                                .putExtra(EXTRA_HIDE_LAST_VIEWED_PRODUCT, true)
                                .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)

                        startActivity(intent)
                        finish()
                    },
                    onBackClick = {
                        setResult(
                            RESULT_OK,
                            Intent().putStringArrayListExtra(
                                ProductListActivity.CHANGED_PRODUCT_IDS,
                                arrayListOf(productId)
                            ),
                        )
                        this.finish()
                    },
                )
            }
        }
    }

    companion object {
        const val EXTRA_HIDE_LAST_VIEWED_PRODUCT = "hideLastViewedProduct"
    }
}
