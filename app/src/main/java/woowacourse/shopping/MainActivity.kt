package woowacourse.shopping

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import woowacourse.shopping.domain.CartItem
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

        var cartItems = emptyList<ProductUiModel>()
        val productListStateHolder = ProductListStateHolder()
        val productUiModels = productListStateHolder.getAllProducts()
            .map(productListStateHolder::toProductUiModel)

        val detailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val addedId = result.data
                ?.getStringExtra("added_to_cart_id")
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.addCartItem(
                CartItem(
                    product = productListStateHolder.products.first { it.id == addedId },
                    quantity = Quantity(1),
                ),
            )
            cartItems = productListStateHolder.toProductUiModels()
            Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

        val cartLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val deletedCartItems = result.data
                ?.getParcelableArrayListExtra("deleted_cart_list", ProductUiModel::class.java)
                ?: return@registerForActivityResult

            productListStateHolder.cart = productListStateHolder.replaceCartItems(deletedCartItems)
            cartItems = productListStateHolder.toProductUiModels()
        }

        setContent {
            AndroidshoppingTheme {
                ProductListScreen(
                    productUiModels = productUiModels,
                    onProductClick = { id ->
                        detailLauncher.launch(
                            Intent(this, ProductDetailActivity::class.java)
                                .putExtra("product_id", id),
                        )
                    },
                    onCartIconClick = {
                        cartLauncher.launch(
                            Intent(this, CartActivity::class.java)
                                .putParcelableArrayListExtra("extra_cart_items", ArrayList(cartItems)),
                        )
                    },
                )
            }
        }
    }
}
