package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productdetail.mapper.toUiModel
import woowacourse.shopping.presentation.shopping.screen.ProductListScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                val viewModel: ProductListViewModel by viewModels {
                    ProductListViewModelFactory(
                        productRepository = AppContainer.productRepository,
                    )
                }
                ProductListScreen(
                    products = viewModel.products,
                    hasNextPage = viewModel.hasNextPage,
                    onLoadMore = viewModel::loadMore,
                    onCartIconClick = {
                        startActivity(CartActivity.newIntent(this))
                    },
                    onItemClick = { product ->
                        startActivity(ProductDetailActivity.newIntent(this, product.toUiModel()))
                    },
                )
            }
        }
    }
}
