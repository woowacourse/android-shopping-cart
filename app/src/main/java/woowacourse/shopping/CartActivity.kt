package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.repository.CartRepository
import woowacourse.shopping.ui.stateholder.CartStateHolder

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val stateHolder = rememberSaveable { CartStateHolder() }
            val currentPageProducts = remember {
                mutableStateOf(
                    CartRepository.getPartedItem(
                        stateHolder.currentPage,
                        PAGE_SIZE
                    )
                )
            }

            BackHandler {
                finish()
            }

            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                CartScreen(
                    cart = currentPageProducts.value,
                    onClose = {
                        finish()
                    },
                    onDelete = { id ->
                        CartRepository.removeProduct(id)
                        currentPageProducts.value =
                            CartRepository.getPartedItem(stateHolder.currentPage, PAGE_SIZE)
                        if (currentPageProducts.value.isEmpty()) {
                            stateHolder.onPrevious()
                            currentPageProducts.value =
                                CartRepository.getPartedItem(stateHolder.currentPage, PAGE_SIZE)
                        }
                    },
                    currentPage = stateHolder.currentPage,
                    onPrevious = {
                        stateHolder.onPrevious()
                        currentPageProducts.value =
                            CartRepository.getPartedItem(stateHolder.currentPage, PAGE_SIZE)
                    },
                    onNext = {
                        stateHolder.onNext(CartRepository.size())
                        currentPageProducts.value =
                            CartRepository.getPartedItem(stateHolder.currentPage, PAGE_SIZE)
                    },
                    previousEnable = stateHolder.checkPreviousAvailable(),
                    nextEnable = stateHolder.checkNextAvailable(CartRepository.size()),
                    isPageable = CartRepository.size() > PAGE_SIZE,
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
