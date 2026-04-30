package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Quantity
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
            Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

        val cartLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val deletedIdsText = result.data
                ?.getStringExtra("added_to_cart_id")
                ?: return@registerForActivityResult

            val deletedIds = deletedIdsText.split(",")
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
                        val cartUiModels = productListStateHolder.cart.cartItems
                            .map { productListStateHolder.toProductUiModel(it.product) }
                        cartLauncher.launch(
                            Intent(this, CartActivity::class.java)
                                .putParcelableArrayListExtra("extra_cart_items", ArrayList(cartUiModels)),
                        )
                    },
                )
            }
        }
    }
}
