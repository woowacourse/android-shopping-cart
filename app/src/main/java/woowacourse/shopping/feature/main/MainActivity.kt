package woowacourse.shopping.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.feature.main.adapter.ProductAdapter
import woowacourse.shopping.viewmodel.ProductsViewModel
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: ProductAdapter
    private val productsViewModel by viewModels<ProductsViewModel>()
    private val productRepository: ProductRepository by lazy { ProductRepositoryImpl }
    private var page: Int = 0

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
        updateProducts()
    }

    private fun initializeProductAdapter() {
        adapter =
            ProductAdapter(onClickProductItem = { productId ->
                ProductDetailActivity.newIntent(this, productId)
                    .also { startActivity(it) }
            })
        binding.rvMainProduct.adapter = adapter

        productsViewModel.products.observe(this) {
            val itemCount = min(it.size, PAGE_SIZE)
            adapter.updateProducts(it, page++ * PAGE_SIZE, itemCount)
        }
    }

    private fun initializeToolbar() {
        binding.toolbarMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }
    }

    private fun updateProducts() {
        productsViewModel.loadPage(productRepository, page, PAGE_SIZE)
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
            updateProducts()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
