package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.viewmodel.CartViewModel
import woowacourse.shopping.ui.viewmodel.CartViewModelFactory

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: CartViewModel =
                viewModel<CartViewModel>(
                    factory =
                        CartViewModelFactory(
                            (application as ShoppingApplication).purchaseProductsRepository,
                            (application as ShoppingApplication).productRepository
                        ),
                )

            val pagedCart by viewModel.pagedCart.collectAsStateWithLifecycle()
            val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
            val isPageable by viewModel.isPageable.collectAsStateWithLifecycle()
            val nextEnable by viewModel.nextEnable.collectAsStateWithLifecycle()
            val prevEnable by viewModel.prevEnable.collectAsStateWithLifecycle()

            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                CartScreen(
                    cart = pagedCart.purchaseProducts,
                    onClose = {
                        finish()
                    },
                    onAdd = { id, updateAmount ->
                        viewModel.updateCountWithID(id, updateAmount)
                    },
                    onMinus = { id, updateAmount ->
                        viewModel.updateCountWithID(id, updateAmount)
                    },
                    onDelete = { id ->
                        viewModel.removeWithID(id)
                    },
                    currentPage = currentPage,
                    onPrevious = {
                        viewModel.prev()
                    },
                    onNext = {
                        viewModel.next()
                    },
                    previousEnable = prevEnable,
                    nextEnable = nextEnable,
                    isPageable = isPageable,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                )
            }
        }
    }
}
