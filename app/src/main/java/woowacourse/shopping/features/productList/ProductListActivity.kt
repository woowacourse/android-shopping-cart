package woowacourse.shopping.features.productList

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import woowacourse.shopping.features.cart.CartActivity
import woowacourse.shopping.features.productDetail.ProductDetailActivity

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val scope = rememberCoroutineScope()
                val stateHolder = retainProductListStateHolder()
                ProductListScreen(
                    productList = stateHolder.products,
                    isLastPage = stateHolder.isLastPage,
                    modifier = Modifier.padding(innerPadding),
                    onCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        startActivity(cartIntent)
                    },
                    onProductClick = { product ->
                        val detailIntent =
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("PRODUCT_ID", product.id)
                            }
                        startActivity(detailIntent)
                    },
                    loadProducts = {
                        stateHolder.loadProducts()
                    },
                )
            }
        }
    }
}
