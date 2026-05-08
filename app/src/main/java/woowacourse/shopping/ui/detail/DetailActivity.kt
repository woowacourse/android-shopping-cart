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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.MockProductRepository
import woowacourse.shopping.data.localdb.ShoppingDB
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(PRODUCT_ID)

        if (id == null) {
            Toast.makeText(this, "유효하지 않은 상품입니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val database = ShoppingDB.getInstance(applicationContext)
        val viewModel: DetailViewModel by viewModels {
            DetailViewModel.provideFactory(
                id = id,
                productRepository = MockProductRepository(),
                cartRepository = CartRepository(database.cartItemDao()),
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

                            DetailEvent.ShowAddCartFailureMessage -> {
                                Toast.makeText(
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
                    onIncreaseQuantity = viewModel::increaseQuantity,
                    onDecreaseQuantity = viewModel::decreaseQuantity,
                    onAddToCart = viewModel::addToCart,
                    modifier = Modifier,
                )
            }
        }
    }

    companion object {
        private const val PRODUCT_ID = "id"

        fun getIntent(
            context: Context,
            id: String,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, id)
            }
    }
}
