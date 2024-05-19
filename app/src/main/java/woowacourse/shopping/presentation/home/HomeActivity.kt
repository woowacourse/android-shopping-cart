package woowacourse.shopping.presentation.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityHomeBinding
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
            application.productRepository
        )
    }
    private val adapter: ProductAdapter by lazy {
        ProductAdapter(viewModel)
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_shopping_cart -> {
                startActivity(CartActivity.newIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeProductListLayout() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = ProductItemSpanSizeLookup(adapter)
        binding.rvHome.layoutManager = layoutManager
    }

    private fun initializeBindingVariables() {
        binding.productAdapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun observeEvents() {
        viewModel.navigateToDetailEvent.observe(this) { event ->
            startActivity(
                DetailActivity.newIntent(
                    this,
                    event.getContentIfNotHandled() ?: return@observe
                )
            )
        }
    }
}

@BindingAdapter("shopping:data")
fun <T> RecyclerView.setData(
    data: List<T>?,
) {
    if (data == null) return
    if (adapter is BindableAdapter<*>) {
        (adapter as BindableAdapter<T>).setData(data)
    }
}

@BindingAdapter("shopping:loadStatus")
fun RecyclerView.setLoadStatus(
    loadStatus: LoadStatus?,
) {
    if (loadStatus == null) return
    if (adapter is ProductAdapter) {
        (adapter as ProductAdapter).updateLoadStatus(loadStatus)
    }
}
