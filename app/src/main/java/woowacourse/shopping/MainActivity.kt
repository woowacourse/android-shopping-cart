package woowacourse.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.productlist.ProductListScreen
import woowacourse.shopping.ui.productlist.stateholder.ProductListStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val productListStateHolder = ProductListStateHolder()

            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                productListStateHolder.loadInitialProducts()
            }

            val detailLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                if (result.resultCode != RESULT_OK) return@rememberLauncherForActivityResult
                val addedId = ProductDetailActivity.getAddedId(result.data)
                    ?: return@rememberLauncherForActivityResult

                val isSuccess = productListStateHolder.addCartItem(addedId = addedId)

                if (isSuccess) {
                    Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "상품 정보를 찾을 수 없어 추가에 실패했습니다", Toast.LENGTH_SHORT).show()
                }
            }

            val cartLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                if (result.resultCode != RESULT_OK) return@rememberLauncherForActivityResult
                val deletedCartItems = CartActivity.getDeletedList(result.data)
                    ?: return@rememberLauncherForActivityResult

                productListStateHolder.syncDeletedCartItems(deletedCartItems)
            }
            AndroidshoppingTheme {
                ProductListScreen(
                    productUiModels = productListStateHolder.productUiModels,
                    isEnd = productListStateHolder.isEndList(),
                    onLoading = {
                        scope.launch {
                            productListStateHolder.fetchProducts()
                        }
                    },
                    onProductClick = { id ->
                        detailLauncher.launch(ProductDetailActivity.newIntent(this, id))
                    },
                    onCartIconClick = {
                        cartLauncher.launch(CartActivity.newIntent(this, productListStateHolder.cartUiModels))
                    },
                )
            }
        }
    }
}
