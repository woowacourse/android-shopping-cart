package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.component.CartScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val restoredPage = savedInstanceState?.getInt("CURRENT_PAGE") ?: 0
        val app = application as ShoppingApplication
        viewModel =
            ViewModelProvider(
                this,
                CartViewModel.provideFactory(app.cartRepository, restoredPage),
            )[CartViewModel::class.java]

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        setContent {
            AndroidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    CartScreen(
                        onDelete = { uuid -> viewModel.onDeleteProduct(uuid) },
                        onNext = { viewModel.onNext() },
                        onPrevious = { viewModel.onPrevious() },
                        onIncrease = { uuid -> viewModel.onIncreaseProduct(uuid) },
                        onDecrease = { uuid -> viewModel.onDecreaseProduct(uuid) },
                        previousEnable = uiState.hasPreviousPage,
                        nextEnable = uiState.hasNextPage,
                        currentPage = uiState.currentPage,
                        onClose = {
                            finish()
                        },
                        getPartedItem = { uuid -> viewModel.getPartedItem(uuid) },
                        isPageable = { uiState.isPageable },
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_PAGE", viewModel.uiState.value.currentPage)
    }
}
