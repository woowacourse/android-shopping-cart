package woowacourse.shopping.presentation.cart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.ui.CartScreen
import woowacourse.shopping.presentation.cart.viewmodel.CartEvent
import woowacourse.shopping.presentation.cart.viewmodel.CartViewModel
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    viewModel.refreshCart()
                }

                LaunchedEffect(Unit) {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.uiEvents.collect { event ->
                            val toastMessage =
                                when (event) {
                                    is CartEvent.DeleteSuccess -> R.string.delete_item_success
                                    is CartEvent.DeleteNotFound -> R.string.not_found_item
                                }
                            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                CartScreen(
                    uiState = uiState,
                    onBack = { finish() },
                    onNextPage = { viewModel.nextPage() },
                    onPreviousPage = { viewModel.previousPage() },
                    onDeleteItem = { viewModel.deleteItem(it) },
                    onIncrease = { viewModel.increase(it) },
                    onDecrease = { viewModel.decrease(it) },
                )
            }
        }
    }
}
