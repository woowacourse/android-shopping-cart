package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.di.AppContainer
import woowacourse.shopping.ui.common.theme.ShoppingTheme

class CartActivity : ComponentActivity() {
    val cartRepo = AppContainer.cartRepository
    val pageSize = 5

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: CartViewModel =
                        viewModel(
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T =
                                        CartViewModel(
                                            cartRepo = cartRepo,
                                            pageSize = pageSize,
                                        ) as T
                                },
                        )

                    CartScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = ::finish,
                    )
                }
            }
        }
    }
}
