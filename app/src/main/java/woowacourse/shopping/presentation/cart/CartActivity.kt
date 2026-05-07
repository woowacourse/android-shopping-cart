package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.screen.CartScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.getValue
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            cartRepository = AppContainer.cartRepository,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            androidshoppingTheme {
                CartScreen(
                    cart = viewModel.cart,
                    currentPage = viewModel.currentPage,
                    hasMoreItems = viewModel.hasMoreItems,
                    onPreviousPageClick = viewModel::goToPreviousPage,
                    onNextPageClick = viewModel::goToNextPage,
                    hasPreviousPage = viewModel.hasPreviousPage,
                    hasNextPage = viewModel.hasNextPage,
                    onDelete = viewModel::deleteProduct,
                    onBack = { finish() },
                    onQuantityIncrease = viewModel::increaseQuantity,
                    onQuantityDecrease = viewModel::decreaseQuantity,
                )
            }
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
