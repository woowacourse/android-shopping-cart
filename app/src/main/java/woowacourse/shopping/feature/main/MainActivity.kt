package woowacourse.shopping.feature.main

import android.content.Intent
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.feature.main.adapter.ProductAdapter
import woowacourse.shopping.feature.main.viewmodel.MainViewModel
import woowacourse.shopping.feature.main.viewmodel.MainViewModelFactory
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(ProductDummyRepository, CartDummyRepository) }
    private lateinit var adapter: ProductAdapter
    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initializeBinding()
        initializeProductAdapter()
        initializeToolbar()
        initializeSeeMoreButton()
        initializePage()
        updateProducts()
    }

    private fun initializeBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel
    }

    private fun initializeProductAdapter() {
        adapter =
            ProductAdapter(
                onClickProductItem = { navigateToProductDetailView(productId = it) },
                onClickPlusButton = { mainViewModel.addProductToCart(productId = it) },
                onClickMinusButton = { mainViewModel.deleteProductToCart(productId = it) },
            )
        binding.rvMainProduct.adapter = adapter

        var changedItemCount = 0
        mainViewModel.products.observe(this) { products ->
            changedItemCount = min(products.size, PAGE_SIZE)
            adapter.updateProducts(products, page++ * PAGE_SIZE, changedItemCount)
        }
        mainViewModel.quantities.observe(this) { quantities ->
            adapter.updateQuantities(quantities, page * PAGE_SIZE, changedItemCount)
        }
    }

    private fun navigateToProductDetailView(productId: Long) {
        ProductDetailActivity.newIntent(this, productId)
            .also { startActivity(it) }
    }

    private fun initializeToolbar() {
        binding.btnMainCart.setOnClickListener {
            navigateToCartView()
        }
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun updateProducts() {
        mainViewModel.loadPage(page, PAGE_SIZE)
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
            it.visibility = View.GONE
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
