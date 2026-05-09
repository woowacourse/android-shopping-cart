package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.model.DetailUiState
import woowacourse.shopping.presentation.detail.ui.DetailScreen
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModel
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = this.intent.getLongExtra(INTENT_PRODUCT_ID, -1L)
        val isFromLastSeen = this.intent.getBooleanExtra(IS_FROM_LAST_SEEN, false)
        if (id == -1L) {
            Toast.makeText(this, R.string.invalid_product, Toast.LENGTH_SHORT).show()
            this.finish()
            return
        }

        setContent {
            AndroidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    viewModel.loadProduct(id, isFromLastSeen)
                }

                when (val state = uiState) {
                    is DetailUiState.Loading -> CircularProgressIndicator()
                    is DetailUiState.Error -> Toast.makeText(this, (uiState as DetailUiState.Error).message, Toast.LENGTH_SHORT).show()
                    is DetailUiState.Success -> {
                        DetailScreen(
                            uiState = state,
                            onClickLastProductCard = { lastProductId ->
                                val intent =
                                    Intent(this, DetailActivity::class.java).apply {
                                        putExtra(INTENT_PRODUCT_ID, lastProductId)
                                        putExtra(IS_FROM_LAST_SEEN, true)
                                    }
                                startActivity(intent)
                                finish()
                            },
                            onBack = { finish() },
                            onAddToCart = {
                                viewModel.addToCart(id, state.quantity)
                                val intent = Intent(this, CartActivity::class.java)
                                startActivity(intent)
                            },
                            onIncrease = { viewModel.increase() },
                            onDecrease = { viewModel.decrease() },
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val INTENT_PRODUCT_ID = "id"
        private const val IS_FROM_LAST_SEEN = "is_from_last_seen"

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent =
            Intent(context, DetailActivity::class.java)
                .putExtra(INTENT_PRODUCT_ID, productId)
    }
}
