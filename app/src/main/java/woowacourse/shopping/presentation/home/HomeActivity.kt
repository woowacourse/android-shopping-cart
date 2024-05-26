package woowacourse.shopping.presentation.home

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.datasourceimpl.DefaultCartDataSource
import woowacourse.shopping.data.datasourceimpl.DefaultProductDataSource
import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.repository.DefaultCartRepository
import woowacourse.shopping.data.repository.DefaultProductRepository
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.cart.CartActivity.Companion.CART_RESULT_OK
import woowacourse.shopping.presentation.cart.CartActivity.Companion.EXTRA_CART_ITEMS
import woowacourse.shopping.presentation.detail.DetailActivity
import woowacourse.shopping.presentation.detail.DetailActivity.Companion.DETAIL_RESULT_OK
import woowacourse.shopping.presentation.detail.DetailActivity.Companion.EXTRA_DETAIL_PRODUCT_ID
import woowacourse.shopping.presentation.home.adapter.ProductAdapter
import woowacourse.shopping.presentation.home.adapter.ProductsGridLayoutManager
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModel
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {
    private val detailActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == DETAIL_RESULT_OK) {
                val productId = result?.data?.getLongExtra(EXTRA_DETAIL_PRODUCT_ID, DEFAULT_DETAIL_PRODUCT_ID)

                viewModel.updateOrder(productId!!)
            }
        }

    private val cartActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == CART_RESULT_OK) {
                val productIds = result?.data?.getLongArrayExtra(EXTRA_CART_ITEMS)

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
                DefaultProductRepository(DefaultProductDataSource),
                DefaultCartRepository(DefaultCartDataSource(CartDatabase.getInstance(this))),
            ),
        )[HomeViewModel::class.java]
    }

    private val adapter: ProductAdapter by lazy {
        ProductAdapter(viewModel, viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTotalCartCount()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBinding()
        initObserver()
        initToolBar()
    }

    private fun initBinding() {
        binding.rvHome.layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup = ProductsGridLayoutManager(adapter)
            }
        binding.productAdapter = adapter
        binding.homeViewModel = viewModel
        binding.homeActionHandler = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObserver() {
        viewModel.orders.observe(this) {
            adapter.addProducts(it)
        }

        viewModel.updateOrder.observe(this) {
            adapter.updateProduct(it)
        }

        viewModel.isLoadingAvailable.observe(this) {
            adapter.updateLoadStatus(it)
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
    }

    private fun initToolBar() {
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {
        private const val DEFAULT_DETAIL_PRODUCT_ID = -1L
    }
}
