package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ShoppingApplication


class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as ShoppingApplication).appContainer
        setContent {
            val viewModel: CartViewModel =
                viewModel(
                    factory = CartViewModel.factory(appContainer.cartRepository),
                )
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                CartScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
