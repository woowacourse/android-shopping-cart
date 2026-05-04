package woowacourse.shopping.feature.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.core.designsystem.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getStringExtra(PRODUCT_ID)
        if (productId.isNullOrBlank()) {
            showErrorAndFinish()
            return
        }

        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainProductDetailStateHolder(productId)

                    ProductDetailScreen(
                        productInfo = stateHolder.productInfo,
                        onCloseClick = { finish() },
                        onAddCartClick = stateHolder::addToCart,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    private fun showErrorAndFinish() {
        Toast.makeText(this, "상품 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        private const val PRODUCT_ID = "product_id"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
    }
}

