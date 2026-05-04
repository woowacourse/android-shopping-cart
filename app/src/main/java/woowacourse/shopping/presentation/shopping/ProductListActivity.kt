package woowacourse.shopping.presentation.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
                var pageIndex by rememberSaveable { mutableStateOf(0) }

                val stateHolder =
                    remember {
                        ProductListStateHolder(
                            productRepository = ProductRepositoryImpl,
                            initialPageIndex = pageIndex,
                            onPageIndexChanged = { pageIndex = it },
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
