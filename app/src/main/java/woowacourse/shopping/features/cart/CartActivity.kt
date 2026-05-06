package woowacourse.shopping.features.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val stateHolder = retainCartStateHolder()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                CartScreen(
                    modifier = Modifier.padding(innerPadding),
                    cartItems = stateHolder.pageCartItems,
                    totalPages = stateHolder.totalPages,
                    currentPage = stateHolder.currentPage,
                    hasPrevious = !stateHolder.isFirstPage(),
                    hasNext = !stateHolder.isLastPage(),
                    goToPreviousPage = { stateHolder.goToPreviousPage() },
                    goToNextPage = { stateHolder.goToNextPage() },
                    removeCartItem = { stateHolder.removeCartItem(it) },
                )
            }
        }
    }
}
