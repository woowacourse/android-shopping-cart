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
import woowacourse.shopping.repository.cart.CartRepositoryMockImpl

class   CartActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = CartViewModel(CartRepositoryMockImpl)
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                CartScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )
            }
        }
    }
}
