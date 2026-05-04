package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ui.theme.ShoppingTheme

class CartActivity : ComponentActivity() {
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CartScreen(
                        items = uiState.items,
                        currentPage = uiState.currentPage,
                        totalPages = uiState.totalPages,
                        showPagination = uiState.totalPages > 1,
                        isLoading = uiState.isLoading,
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = ::finish,
                        onDeleteClick = viewModel::delete,
                        onPreviousClick = viewModel::loadPreviousPage,
                        onNextClick = viewModel::loadNextPage,
                    )
                }
            }
        }
    }
}
