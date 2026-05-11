package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.component.screen.MainScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val networkMonitor = NetworkMonitor(this)

        setContent {
            AndroidshoppingTheme {
                val app = application as ShoppingApplication
                val viewModel: CatalogViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                           return CatalogViewModel(
                               productRepository = app.productRepository,
                               cartRepository = app.cartRepository,
                               recentProductRepository = app.recentProductRepository
                           ) as T
                        }

                    }
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(Unit) {
                        var isInitial = true
                        networkMonitor.isConnected.collect { connected ->
                            if (isInitial) {
                                isInitial = false
                                return@collect
                            }
                            if (!connected) {
                                Toast.makeText(this@MainActivity, "네트워크 연결이 끊겼습니다.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "네트워크가 연결되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    MainScreen(
                        viewModel = viewModel,
                        onItemClick = { id ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("id", id.toString())
                            }
                            startActivity(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java)
                            startActivity(intent)
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
