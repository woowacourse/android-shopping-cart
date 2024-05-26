package woowacourse.shopping.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryDatabase
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryLocalRepository
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.feature.main.adapter.InquiryHistoryAdapter
import woowacourse.shopping.feature.main.adapter.ProductAdapter
import woowacourse.shopping.feature.main.viewmodel.MainViewModel
import woowacourse.shopping.feature.main.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ProductDummyRepository,
            CartDummyRepository,
            InquiryHistoryLocalRepository(InquiryHistoryDatabase.initialize(this).recentViewedProductDao()),
        )
    }
    private lateinit var inquiryHistoryAdapter: InquiryHistoryAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initializeBinding()
        initializeToolbar()
        initializeInquiryHistoryAdapter()
        initializeProductAdapter()
        initializePage()
    }

    private fun initializeBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel
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

    private fun initializeInquiryHistoryAdapter() {
        inquiryHistoryAdapter = InquiryHistoryAdapter()
        binding.rvMainRecentViewedProduct.adapter = inquiryHistoryAdapter

        mainViewModel.inquiryHistories.observe(this) { inquiryHistories ->
            inquiryHistoryAdapter.updateInquiryHistories(inquiryHistories)
        }
    }

    private fun initializeProductAdapter() {
        productAdapter =
            ProductAdapter(
                onClickProductItem = { navigateToProductDetailView(productId = it) },
                onClickPlusButton = { mainViewModel.addProductToCart(productId = it) },
                onClickMinusButton = { mainViewModel.deleteProductToCart(productId = it) },
            )
        binding.rvMainProduct.adapter = productAdapter

        mainViewModel.products.observe(this) { products ->
            productAdapter.updateProducts(products)
        }
        mainViewModel.quantities.observe(this) { quantities ->
            productAdapter.updateQuantities(quantities)
        }
    }

    private fun navigateToProductDetailView(productId: Long) {
        ProductDetailActivity.newIntent(this, productId)
            .also { startActivity(it) }
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
                    mainViewModel.updateSeeMoreStatus(lastPosition, totalCount)
                }
            }
        binding.rvMainProduct.addOnScrollListener(onScrollListener)
    }
}
