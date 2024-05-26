package woowacourse.shopping.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryDatabase
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryLocalRepository
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
            CartDummyRepository(CartDatabase.initialize(this).cartDao()),
            InquiryHistoryLocalRepository(InquiryHistoryDatabase.initialize(this).recentViewedProductDao()),
        )
    }
    private lateinit var inquiryHistoryAdapter: InquiryHistoryAdapter
    private lateinit var productAdapter: ProductAdapter

    // TODO: 수량이 바로 갱신 되지 않는 오류 해결
    private val cartActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val isUpdateCart = result.data?.getBooleanExtra(UPDATE_CART_STATUS_KEY, false)
//                if (isUpdateCart == true) {
//                    mainViewModel.loadPage()
//                }
            }
        }

    private val productDetailActivityLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val isLastViewed = result.data?.getBooleanExtra(LAST_VIEWED_PRODUCT_STATUS_KEY, false)
                if (isLastViewed == true) {
                    mainViewModel.loadInquiryHistory()
                }
            }
        }

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
        cartActivityResultLauncher.launch(intent)
    }

    private fun initializeInquiryHistoryAdapter() {
        inquiryHistoryAdapter =
            InquiryHistoryAdapter(
                onClickInquiryHistory = { navigateToProductDetailView(productId = it) },
            )
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
        val intent =
            Intent(this, ProductDetailActivity::class.java)
                .putExtra(PRODUCT_ID_KEY, productId)
        productDetailActivityLauncher.launch(intent)
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

    companion object {
        const val UPDATE_CART_STATUS_KEY = "isUpdateCart"
        const val LAST_VIEWED_PRODUCT_STATUS_KEY = "isLastViewed"
        const val PRODUCT_ID_KEY = "product_id_key"
    }
}
