package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.productdetail.stateholder.ProductDetailStateHolder
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.productlist.ProductDetailScreen

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(EXTRA_PRODUCT_ID)
        val holder = ProductDetailStateHolder()

        val productModel = id?.let { holder.getProductUiModel(it) }
        if (productModel == null) {
            finish()
            return
        }

        setContent {
            AndroidshoppingcartTheme {
                ProductDetailScreen(
                    imageUrl = productModel.imageUrl,
                    title = productModel.title,
                    price = productModel.price,
                    onCloseClick = { finish() },
                    onAddToCartClick = {
                        setResult(RESULT_OK, addedIdResult(id))
                        finish()
                    },
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "product_id"
        private const val EXTRA_ADDED_ID = "added_to_cart_id"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent = Intent(context, ProductDetailActivity::class.java)
            .putExtra(EXTRA_PRODUCT_ID, productId)

        fun getAddedId(intent: Intent?): String? = intent?.getStringExtra(EXTRA_ADDED_ID)

        private fun addedIdResult(productId: String?): Intent = Intent().putExtra(EXTRA_ADDED_ID, productId)
    }
}
