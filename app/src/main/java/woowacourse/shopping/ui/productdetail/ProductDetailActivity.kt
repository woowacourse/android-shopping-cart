package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import kotlin.jvm.java
import woowacourse.shopping.R
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.productlist.ProductDetailScreen
import woowacourse.shopping.ui.state.ProductUiModel

class ProductDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val uiModel = intent.getParcelableExtra(DETAIL_PRODUCT, ProductUiModel::class.java)
        if (uiModel == null) {
            Toast.makeText(this, R.string.product_detail_entry_error, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            AndroidshoppingcartTheme {
                ProductDetailScreen(
                    imageUrl = uiModel.imageUrl,
                    title = uiModel.title,
                    price = uiModel.price,
                    onCloseClick = { finish() },
                    onAddToCartClick = {
                        setResult(RESULT_OK, addedIdResult(uiModel.id))
                        finish()
                    },
                )
            }
        }
    }

    companion object {
        private const val DETAIL_PRODUCT = "product_id"
        private const val EXTRA_ADDED_ID = "added_to_cart_id"

        fun newIntent(
            context: Context,
            uiModel: ProductUiModel,
        ): Intent = Intent(context, ProductDetailActivity::class.java)
            .putExtra(DETAIL_PRODUCT, uiModel)

        fun getAddedId(intent: Intent?): String? = intent?.getStringExtra(EXTRA_ADDED_ID)

        private fun addedIdResult(productId: String?): Intent = Intent().putExtra(EXTRA_ADDED_ID, productId)
    }
}
