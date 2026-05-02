package woowacourse.shopping

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.productlist.ProductListScreen
import woowacourse.shopping.ui.productlist.stateholder.ProductListStateHolder
import woowacourse.shopping.ui.state.ProductUiModel
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productListStateHolder = ProductListStateHolder()
        val productUiModels: MutableState<List<ProductUiModel>> = mutableStateOf(emptyList())

        lifecycleScope.launch {
            productUiModels.value = productListStateHolder.fetchProducts()
                .map(productListStateHolder::toProductUiModel)
        }

        val detailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val addedId = ProductDetailActivity.getAddedId(result.data)
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.addCartItem(
                CartContent(
                    product = productListStateHolder.products.first { it.id == addedId },
                    quantity = Quantity(1),
                ),
            )
            productListStateHolder.uiModels = productListStateHolder.toProductUiModels()
            Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

        val cartLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val deletedCartItems = CartActivity.getDeletedList(result.data)
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.replaceCartItems(deletedCartItems)
            productListStateHolder.uiModels = productListStateHolder.toProductUiModels()
        }

        setContent {
            AndroidshoppingTheme {
                ProductListScreen(
                    productUiModels = productUiModels.value,
                    isEnd = productListStateHolder.isEndList(),
                    onLoading = {
                        lifecycleScope.launch {
                            productUiModels.value = productListStateHolder.fetchProducts()
                                .map(productListStateHolder::toProductUiModel)
                        }
                    },
                    onProductClick = { id ->
                        detailLauncher.launch(ProductDetailActivity.newIntent(this, id))
                    },
                    onCartIconClick = {
                        cartLauncher.launch(CartActivity.newIntent(this, productListStateHolder.uiModels))
                    },

                )
            }
        }
    }
}
