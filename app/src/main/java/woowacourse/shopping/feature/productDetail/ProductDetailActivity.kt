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
        val isFromRecent = intent.getBooleanExtra(IS_FROM_RECENT, false)
        if (productId.isNullOrBlank()) {
            showErrorAndFinish()
            return
        }

        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainProductDetailStateHolder(productId, isFromRecent)

                    ProductDetailScreen(
                        productInfo = stateHolder.productInfo,
                        previousProductName = if (stateHolder.shouldShowRecentSummary) stateHolder.previousProduct?.productTitle?.value else null,
                        onCloseClick = { finish() },
                        onRecentProductClick = {
                            stateHolder.previousProduct?.id?.let { id ->
                                startActivity(newIntent(this, id, isFromRecent = true))
                                finish()
                            }
                        },
                        onAddCartClick = stateHolder::onAddClick,
                        onIncreaseClick = stateHolder::onIncreaseClick,
                        onDecreaseClick = stateHolder::onDecreaseClick,
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
        private const val IS_FROM_RECENT = "is_from_recent"

        fun newIntent(
            context: Context,
            productId: String,
            isFromRecent: Boolean = false,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
                putExtra(IS_FROM_RECENT, isFromRecent)
            }
    }
}
