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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.data.DataProvider.getCartRepository
import woowacourse.shopping.data.DataProvider.getNetworkMonitor
import woowacourse.shopping.data.DataProvider.getRecentProductRepository
import woowacourse.shopping.data.DataProvider.productRepository
import woowacourse.shopping.features.cart.CartActivity
import woowacourse.shopping.features.productDetail.ProductDetailActivity

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ProductListViewModel =
                viewModel(
                    factory =
                        ProductListViewModelFactory(
                            networkMonitor = getNetworkMonitor(this),
                            productRepository = productRepository,
                            cartRepository = getCartRepository(this),
                            recentProductRepository = getRecentProductRepository(this),
                        ),
                )

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val context = LocalContext.current

                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    viewModel.loadProductUiList()
                    viewModel.loadRecentProducts()
                }

                LaunchedEffect(Unit) {
                    viewModel.uiEvent.collect {
                        when (it) {
                            is ProductUiEvent.ShowToast -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }

                            is ProductUiEvent.NextPage -> {
                                val detailIntent =
                                    ProductDetailActivity.newIntent(
                                        this@ProductListActivity,
                                        it.parcelProduct,
                                    )
                                startActivity(detailIntent)
                            }
                        }
                    }
                }

                ProductListScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                    onCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        startActivity(cartIntent)
                    },
                    onAddCartClick = { product ->
                        viewModel.addCartItem(product)
                    },
                    isExistProductToCart = { product ->
                        viewModel.isExistProduct(product)
                    },
                    onDecrementClick = {
                        viewModel.minusCartItem(it)
                    },
                    onProductClick = {
                        viewModel.isHasProductId(it)
                    },
                    loadProducts = {
                        viewModel.moreProducts()
                    },
                )
            }
        }
    }
}
