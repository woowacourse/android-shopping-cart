package woowacourse.shopping.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.datasource.DefaultCart
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.DetailActivity
import woowacourse.shopping.presentation.home.adapter.LoadClickListener
import woowacourse.shopping.presentation.home.adapter.ProductAdapter
import woowacourse.shopping.presentation.home.adapter.ProductItemClickListener
import woowacourse.shopping.presentation.home.adapter.ProductsGridLayoutManager
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModel
import woowacourse.shopping.presentation.home.viewmodel.HomeViewModelFactory

class HomeActivity : AppCompatActivity(), ProductItemClickListener, LoadClickListener, HomeClickListener {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                ProductRepositoryImpl(DefaultProducts),
                CartRepositoryImpl(DefaultCart),
            ),
        )[HomeViewModel::class.java]
    }
    private val adapter: ProductAdapter by lazy {
        ProductAdapter(this, this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTotalCartCount()
        viewModel.loadProducts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup = ProductsGridLayoutManager(adapter)
            }

        binding.rvHome.layoutManager = layoutManager
        binding.productAdapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.homeClickListener = this

        viewModel.products.observe(this) {
            adapter.addProducts(it)
        }
        viewModel.loadStatus.observe(this) {
            adapter.updateLoadStatus(it)
        }

        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onProductItemClick(id: Long) {
        startActivity(DetailActivity.newIntent(this, id))
    }

    override fun addCartItem(id: Long) {
        viewModel.addCartItem(id)
    }

    override fun onLoadClick() {
        viewModel.loadProducts()
    }

    override fun moveToCart() {
        startActivity(CartActivity.newIntent(this))
    }
}
