package woowacourse.shopping.presentation.home

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.datasource.cart.DefaultCartDataSource
import woowacourse.shopping.data.datasource.product.RemoteProductDataSource
import woowacourse.shopping.data.datasource.recent.DefaultRecentProductDataSource
import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.db.producthistory.RecentProductDatabase
import woowacourse.shopping.data.repository.DefaultCartRepository
import woowacourse.shopping.data.repository.DefaultProductRepository
import woowacourse.shopping.data.repository.DefaultRecentRecentProductRepository
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.cart.CartActivity.Companion.CART_RESULT_OK
import woowacourse.shopping.presentation.cart.CartActivity.Companion.EXTRA_CART_ITEMS
import woowacourse.shopping.presentation.detail.DetailActivity
import woowacourse.shopping.presentation.detail.DetailActivity.Companion.DETAIL_RESULT_OK
import woowacourse.shopping.presentation.detail.DetailActivity.Companion.EXTRA_DETAIL_PRODUCT_ID
import woowacourse.shopping.presentation.home.adapter.ProductAdapter
import woowacourse.shopping.presentation.home.adapter.ProductsGridLayoutManager
import woowacourse.shopping.presentation.home.adapter.RecentProductAdapter
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModel
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {
    private val detailActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == DETAIL_RESULT_OK) {
                val productId =
                    result?.data?.getLongExtra(EXTRA_DETAIL_PRODUCT_ID, DEFAULT_DETAIL_PRODUCT_ID)
                        ?: throw IllegalArgumentException(EXCEPTION_ILLEGAL_PRODUCT_ID)

                viewModel.loadTotalCartCount()
                viewModel.updateOrder(productId)
            }
            viewModel.loadRecentProducts()
        }

    private val cartActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == CART_RESULT_OK) {
                val productIds = result?.data?.getLongArrayExtra(EXTRA_CART_ITEMS)

                viewModel.loadTotalCartCount()
                productIds?.forEach {
                    viewModel.updateOrder(it)
                }
            }
        }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                DefaultProductRepository(RemoteProductDataSource()),
                DefaultCartRepository(DefaultCartDataSource(CartDatabase.getInstance(this))),
                DefaultRecentRecentProductRepository(
                    DefaultRecentProductDataSource(
                        RecentProductDatabase.getInstance(this),
                    ),
                ),
            ),
        )[HomeViewModel::class.java]
    }

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(viewModel, viewModel)
    }

    private val recentProductAdapter: RecentProductAdapter by lazy {
        RecentProductAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBinding()
        initObserver()
        initToolBar()
    }

    private fun initBinding() {
        binding.rvProduct.layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup = ProductsGridLayoutManager(productAdapter)
            }
        binding.productAdapter = productAdapter
        binding.recentProductAdapter = recentProductAdapter
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObserver() {
        viewModel.orders.observe(this) {
            productAdapter.addProducts(it)
        }

        viewModel.updateOrder.observe(this) {
            productAdapter.updateProduct(it)
        }

        viewModel.isLoadingAvailable.observe(this) {
            productAdapter.updateLoadStatus(it)
        }

        viewModel.onProductClicked.observe(this) {
            it.getContentIfNotHandled()?.let { productId ->
                detailActivityResultLauncher.launch(DetailActivity.newIntent(this, productId))
            }
        }

        viewModel.onCartClicked.observe(this) {
            it.getContentIfNotHandled()?.let {
                cartActivityResultLauncher.launch(CartActivity.newIntent(this))
            }
        }

        viewModel.recentProducts.observe(this) {
            recentProductAdapter.setProducts(it)
        }
    }

    private fun initToolBar() {
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {
        private const val DEFAULT_DETAIL_PRODUCT_ID = -1L
        private const val EXCEPTION_ILLEGAL_PRODUCT_ID = "존재하지 않는 제품 ID 입니다."
    }
}
