package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    companion object {
        fun getIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }

    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                CartScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { finish() },
                    onDeleteItem = { viewModel.deleteItem(it) },
                    onNextPage = viewModel::nextPage,
                    onPreviousPage = viewModel::previousPage,
                    onIncreaseQuantity = { viewModel.increaseQuantity(it) },
                    onDecreaseQuantity = { viewModel.decreaseQuantity(it) },
                )
            }
        }
    }
}
