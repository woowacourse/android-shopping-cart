package woowacourse.shopping.presentation.home

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.databinding.LayoutCartCountBinding
import woowacourse.shopping.presentation.BindableAdapter
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.DetailActivity

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }
    private val viewModel: HomeViewModel by viewModels {
        val application = application as ShoppingApplication
        HomeViewModelFactory(
            application.productRepository,
            application.cartRepository,
            application.productHistoryRepository,
        )
    }
    private val productAdapter: ProductAdapter by lazy { ProductAdapter(viewModel, viewModel) }
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter(viewModel) }
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val quantities =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableArrayListExtra(
                            "quantities",
                            ProductQuantity::class.java,
                        )
                    } else {
                        result.data?.getParcelableArrayListExtra("quantities")
                    }
                quantities?.forEach {
                    viewModel.onQuantityChange(it.productId, it.quantity)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeProductListLayout()
        initializeBindingVariables()
        initializeToolbar()
        observeEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val layoutCartCountBinding = LayoutCartCountBinding.inflate(layoutInflater)
        layoutCartCountBinding.viewModel = viewModel
        val menuItem = menu?.findItem(R.id.menu_shopping_cart)
        menuItem?.setActionView(layoutCartCountBinding.root)
        viewModel.totalQuantity.observe(this) {
            layoutCartCountBinding.tvBadgeQuantity.text = it.toString()
        }
        super.onCreateOptionsMenu(menu)
        return true
    }

    private fun initializeProductListLayout() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = ProductItemSpanSizeLookup(productAdapter)
        binding.rvHome.layoutManager = layoutManager
    }

    private fun initializeBindingVariables() {
        binding.productAdapter = productAdapter
        binding.historyAdapter = historyAdapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun observeEvents() {
        viewModel.navigateToDetailEvent.observe(this) { event ->
            val data = event.getContentIfNotHandled()
            activityResultLauncher.launch(
                DetailActivity.newIntent(
                    this,
                    data?.productId ?: return@observe,
                    data.isLastlyViewed,
                ),
            )
        }
        viewModel.navigateToCartEvent.observe(this) { event ->
            event.getContentIfNotHandled()
            activityResultLauncher.launch(CartActivity.newIntent(this))
        }
        viewModel.products.observe(this) {
            productAdapter.setData(it)
        }
        viewModel.changedPosition.observe(this) {
            productAdapter.notifyItemChanged(it.getContentIfNotHandled() ?: return@observe)
        }
    }
}

@BindingAdapter("shopping:data")
fun <T> RecyclerView.setData(data: List<T>?) {
    if (data == null) return
    if (adapter is BindableAdapter<*>) {
        (adapter as BindableAdapter<T>).setData(data)
    }
}

@BindingAdapter("shopping:loadStatus")
fun RecyclerView.setLoadStatus(loadStatus: LoadStatus?) {
    if (loadStatus == null) return
    if (adapter is ProductAdapter) {
        (adapter as ProductAdapter).updateLoadStatus(loadStatus)
    }
}
