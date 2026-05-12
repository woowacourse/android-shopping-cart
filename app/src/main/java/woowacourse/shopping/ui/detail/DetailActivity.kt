package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(PRODUCT_ID)
        val hideRecentItem = intent.getBooleanExtra(HIDE_RECENT_ITEM, false)

        if (id == null) {
            Toast.makeText(this, "유효하지 않은 상품입니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val appContainer = (application as ShoppingApplication).appContainer
        val viewModel: DetailViewModel by viewModels {
            DetailViewModel.provideFactory(
                id = id,
                hideRecentItem = hideRecentItem,
                productRepository = appContainer.productRepository,
                cartRepository = appContainer.cartRepository,
                recentItemRepository = appContainer.recentItemRepository,
            )
        }

        setContent {
            AndroidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(viewModel) {
                    viewModel.event.collect { event ->
                        when (event) {
                            DetailEvent.NavigateToCart -> {
                                startActivity(CartActivity.getIntent(this@DetailActivity))
                            }

                            DetailEvent.NavigateBack -> {
                                finish()
                            }

                            DetailEvent.ShowProductNotFoundMessage -> {
                                Toast
                                    .makeText(
                                        this@DetailActivity,
                                        "상품을 찾을 수 없습니다.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                            }

                            DetailEvent.ShowProductLoadFailureMessage -> {
                                Toast
                                    .makeText(
                                        this@DetailActivity,
                                        "상품 정보를 불러오지 못했습니다.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                            }

                            DetailEvent.ShowAddCartFailureMessage -> {
                                Toast
                                    .makeText(
                                        this@DetailActivity,
                                        "장바구니에 상품을 담지 못했습니다.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                            }
                        }
                    }
                }

                DetailScreen(
                    uiState = uiState,
                    onCloseClick = { finish() },
                    onQuantityChange = viewModel::updateQuantity,
                    onAddToCart = viewModel::addToCart,
                    onRecentItemClick = { id ->
                        startActivity(getIntent(this, id, hideRecentItem = true))
                        finish()
                    },
                    modifier = Modifier,
                )
            }
        }
    }

    companion object {
        private const val PRODUCT_ID = "id"
        private const val HIDE_RECENT_ITEM = "hide_recent_item"

        fun getIntent(
            context: Context,
            id: String,
            hideRecentItem: Boolean = false,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, id)
                putExtra(HIDE_RECENT_ITEM, hideRecentItem)
            }
    }
}
