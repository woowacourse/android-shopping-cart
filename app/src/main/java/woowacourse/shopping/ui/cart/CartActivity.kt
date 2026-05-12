package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    companion object {
        fun getIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }

    private val viewModel: CartViewModel by viewModels {
        val appContainer = (application as ShoppingApplication).appContainer
        CartViewModel.provideFactory(
            cartRepository = appContainer.cartRepository,
            productRepository = appContainer.productRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AndroidshoppingTheme {
                CartScreen(
                    uiState = uiState,
                    onBackClick = { finish() },
                    onDeleteItem = { viewModel.deleteItem(it) },
                    onNextPage = viewModel::nextPage,
                    onPreviousPage = viewModel::previousPage,
                    onQuantityChange = viewModel::updateQuantity,
                )
            }
        }
    }
}
