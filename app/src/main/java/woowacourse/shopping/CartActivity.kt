package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProducts
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder
import kotlin.math.min

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val stateHolder = rememberSaveable(saver = CartStateHolder.Saver) { CartStateHolder(0) }
            var cart by rememberSaveable {
                mutableStateOf(
                    intent.getParcelableExtra<Cart>(IntentKeys.CART_KEY)!!,
                )
            }
            var displayedProducts by remember { mutableStateOf(PurchaseProducts()) }

            LaunchedEffect(cart, stateHolder.currentPage) {
                displayedProducts = cart.getPartedItem(stateHolder.currentPage, PAGE_SIZE)
            }

            BackHandler {
                intent.putExtra(IntentKeys.CART_KEY, cart)
                setResult(RESULT_OK, intent)
                finish()
            }

            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                CartScreen(
                    cart = displayedProducts,
                    onClose = {
                        intent.putExtra(IntentKeys.CART_KEY, cart)
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    onAdd = { id, updateAmount ->
                        cart = cart.updateCountWithId(id, updateAmount)
                    },
                    onMinus = { id, updateAmount ->
                        cart = cart.updateCountWithId(id, updateAmount)
                    },
                    onDelete = { id ->
                        cart = cart.removeWithId(id)
                        if (stateHolder.isEmptyPage(cart.productCount(), PAGE_SIZE)) stateHolder.onPrevious()
                    },
                    currentPage = stateHolder.currentPage,
                    onPrevious = {
                        stateHolder.onPrevious()
                    },
                    onNext = {
                        stateHolder.onNext(cart.productCount())
                    },
                    previousEnable = stateHolder.checkPreviousAvailable(),
                    nextEnable = stateHolder.checkNextAvailable(cart.productCount()),
                    isPageable = cart.productCount() > PAGE_SIZE,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                )
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}

private suspend fun Cart.getPartedItem(
    page: Int,
    pageSize: Int,
): PurchaseProducts {
    val fromIndex = min(page * pageSize, productCount())
    val toIndex = min(fromIndex + pageSize, productCount())
    return PurchaseProducts(totalProducts().subList(fromIndex, toIndex))
}

private fun Cart.productCount() = purchaseProducts.productCount()

private fun Cart.totalProducts() = purchaseProducts.purchaseProducts

private fun PurchaseProducts.productCount() = purchaseProducts.size
