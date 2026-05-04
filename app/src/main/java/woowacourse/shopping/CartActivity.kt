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
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val stateHolder = rememberSaveable(saver = CartStateHolder.Saver) { CartStateHolder(0) }
            var cart by rememberSaveable {
                mutableStateOf(
                    intent.getParcelableExtra<Cart>(IntentKeys.CART_KEY)!!
                )
            }
            var displayedProducts by remember { mutableStateOf(Products()) }

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
                    onDelete = { id ->
                        cart = cart.removeProduct(id)
                        if (stateHolder.isEmptyPage(cart.size(), PAGE_SIZE)) stateHolder.onPrevious()
                    },
                    currentPage = stateHolder.currentPage,
                    onPrevious = {
                        stateHolder.onPrevious()
                    },
                    onNext = {
                        stateHolder.onNext(cart.size())
                    },
                    previousEnable = stateHolder.checkPreviousAvailable(),
                    nextEnable = stateHolder.checkNextAvailable(cart.size()),
                    isPageable = cart.size() > PAGE_SIZE,
                    modifier = Modifier
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
): Products {
    return products.getPartedItem(page, pageSize)
}
