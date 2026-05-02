package woowacourse.shopping.presentation.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.navigation.IntentKeys
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.shopping.screen.ProductListScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                val stateHolder =
                    remember {
                        ProductListStateHolder(
                            productRepository = ProductRepositoryImpl,
                        )
                    }
                ProductListScreen(
                    products = stateHolder.products,
                    hasNextPage = stateHolder.hasNextPage,
                    onLoadMore = { stateHolder.loadMore() },
                    onCartIconClick = {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    },
                    onItemClick = { productId ->
                        val intent =
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(IntentKeys.PRODUCT_ID, productId)
                            }
                        startActivity(intent)
                    },
                )
            }
        }
    }
}
