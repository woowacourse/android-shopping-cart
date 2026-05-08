package woowacourse.shopping.features.productList

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import woowacourse.shopping.features.cart.CartActivity
import woowacourse.shopping.features.productDetail.ProductDetailActivity
import woowacourse.shopping.features.productDetail.ProductDetailStateHolder

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val stateHolder = retainProductListStateHolder()
                val context = LocalContext.current

                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    stateHolder.loadProductUiList()
                }

                ProductListScreen(
                    productList = stateHolder.productUiList,
                    totalCartItemsCount = stateHolder.totalCartItemCount,
                    isLastPage = stateHolder.isLastPage,
                    modifier = Modifier.padding(innerPadding),
                    onCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        startActivity(cartIntent)
                    },
                    onAddCartClick = { product ->
                        stateHolder.addCartItem(product)
                    },
                    getQuantity = { product ->
                        stateHolder.getQuantity(product)
                    },
                    isExistProductToCart = { product ->
                        stateHolder.isExistProduct(product)
                    },
                    onDecrementClick = {
                        stateHolder.minusCartItem(it)
                    },
                    onProductClick = { productUi ->
                        if (!stateHolder.isHasProductId(productUi.id)) {
                            Toast.makeText(context, "상품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                            return@ProductListScreen
                        }

                        val detailIntent =
                            ProductDetailActivity.newIntent(
                                this,
                                ProductDetailStateHolder.from(stateHolder.toProductUi(productUi)),
                            )
                        startActivity(detailIntent)
                    },
                    loadProducts = {
                        stateHolder.moreProducts()
                    },
                )
            }
        }
    }
}
