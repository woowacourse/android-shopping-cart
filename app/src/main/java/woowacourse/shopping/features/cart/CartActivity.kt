package woowacourse.shopping.features.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.data.DataProvider

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CartViewModel = viewModel(
                factory = CartViewModelFactory(
                    cartRepository = DataProvider.cartRepository
                )
            )

            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                CartScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    isMinusEnabled = { viewModel.isMinusEnabled(it) },
                    goToPreviousPage = { viewModel.goToPreviousPage() },
                    goToNextPage = { viewModel.goToNextPage() },
                    removeCartItem = { viewModel.removeCartItem(it) },
                    increaseCartItem = { viewModel.increaseCartItem(it) },
                    decreaseCartItem = { viewModel.decreaseCartItem(it) },
                )
            }
        }
    }
}
