package woowacourse.shopping.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.feature.main.adapter.MainViewModelFactory
import woowacourse.shopping.feature.main.adapter.ProductAdapter
import woowacourse.shopping.feature.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory(ProductDummyRepository) }
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initializeProductAdapter()
        initializeToolbar()
        initializeSeeMoreButton()
        initializePage()
        viewModel.loadPage()
    }

    private fun initializeProductAdapter() {
        adapter =
            ProductAdapter(onClickProductItem = { productId ->
                navigateToProductDetailView(productId)
            })
        binding.rvMainProduct.adapter = adapter

        viewModel.products.observe(this) {
            adapter.updateProducts(it)
        }
    }

    private fun navigateToProductDetailView(productId: Long) {
        ProductDetailActivity.newIntent(this, productId)
            .also { startActivity(it) }
    }

    private fun initializeToolbar() {
        binding.toolbarMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_cart -> {
                    navigateToCartView()
                }
            }
            false
        }
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun initializePage() {
        val onScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition = (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    val totalCount = recyclerView.adapter?.itemCount
                    binding.btnMainSeeMore.visibility = if (isSeeMore(lastPosition, totalCount)) View.VISIBLE else View.GONE
                }
            }
        binding.rvMainProduct.addOnScrollListener(onScrollListener)
    }

    private fun isSeeMore(
        lastPosition: Int,
        totalCount: Int?,
    ): Boolean {
        return (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == totalCount
    }

    private fun initializeSeeMoreButton() {
        binding.btnMainSeeMore.setOnClickListener {
            viewModel.loadPage()
            it.visibility = View.GONE
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
