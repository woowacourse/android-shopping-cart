package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kotlin.jvm.java
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.productlist.ProductDetailErrorScreen
import woowacourse.shopping.ui.productlist.ProductDetailScreen
import woowacourse.shopping.ui.state.ProductUiModel

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val uiModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_PRODUCT, ProductUiModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_PRODUCT)
        }

        setContent {
            AndroidshoppingcartTheme {
                if (uiModel == null) {
                    ProductDetailErrorScreen(onCloseClick = { finish() })
                } else {
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
