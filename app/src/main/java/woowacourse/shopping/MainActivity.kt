package woowacourse.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.CartContentQuantity
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.productlist.ProductListScreen
import woowacourse.shopping.ui.productlist.stateholder.ProductListStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productListStateHolder = ProductListStateHolder()

        val detailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val addedId = ProductDetailActivity.getAddedId(result.data)
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.addCartItem(
                CartContent(
                    product = productListStateHolder.products.first { it.id == addedId },
                    cartContentQuantity = CartContentQuantity(1),
                ),
            )
            productListStateHolder.uiModels = productListStateHolder.toProductUiModels()
            Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

        val cartLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val deletedCartItems = result.data?.let { CartActivity.getDeletedList(it) }
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.replaceCartItems(deletedCartItems.map { it.id })
            productListStateHolder.uiModels = productListStateHolder.toProductUiModels()
        }

        lifecycleScope.launch {
            productListStateHolder.isLoading = true
            productListStateHolder.uiModels = productListStateHolder.fetchProducts()
                .map(productListStateHolder::toProductUiModel)
            productListStateHolder.isLoading = false
        }

        setContent {
            AndroidshoppingTheme {
                ProductListScreen(
                    isLoading = productListStateHolder.isLoading,
                    productUiModels = productListStateHolder.uiModels,
                    isEnd = productListStateHolder.isEndList(),
                    onLoading = {
                        lifecycleScope.launch {
                            productListStateHolder.uiModels = productListStateHolder.fetchProducts()
                                .map(productListStateHolder::toProductUiModel)
                        }
                    },
                    onProductClick = { uiModel ->
                        detailLauncher.launch(ProductDetailActivity.newIntent(this, uiModel))
                    },
                    onCartIconClick = {
                        cartLauncher.launch(CartActivity.newIntent(this, productListStateHolder.uiModels))
                    },

                )
            }
        }
    }
}
