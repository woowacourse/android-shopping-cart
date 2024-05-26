package woowacourse.shopping.presentation.ui.shopping

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyViewedProductsRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.cart.CartActivity.Companion.EXTRA_KEY_MODIFIED_PRODUCT_IDS
import woowacourse.shopping.presentation.ui.detail.DetailActivity
import woowacourse.shopping.presentation.ui.shopping.adapter.RecentlyProductAdapter
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingAdapter

class ShoppingActivity : BaseActivity<ActivityShoppingBinding>(R.layout.activity_shopping) {
    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(
            ShoppingItemsRepositoryImpl((application as ShoppingApplication).appDatabase),
            CartRepositoryImpl((application as ShoppingApplication).appDatabase),
            RecentlyViewedProductsRepositoryImpl((application as ShoppingApplication).appDatabase),
        )
    }

    private lateinit var shoppingAdapter: ShoppingAdapter
    private lateinit var recentlyProductAdapter: RecentlyProductAdapter

    override fun onCreateSetup() {
        initializeViews()
        observeViewModel()
    }

    private fun initializeViews() {
        setUpToolbar()
        setUpShoppingProductRecyclerView()
        setUpRecentlyViewedProductRecyclerView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpToolbar() {
        binding.ivCart.setOnClickListener {
            navigateToShoppingCart()
        }
    }

    private fun setUpShoppingProductRecyclerView() {
        shoppingAdapter = ShoppingAdapter(viewModel = viewModel)
        binding.rvProductList.adapter = shoppingAdapter

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (shoppingAdapter.getItemViewType(position)) {
                        ShoppingAdapter.VIEW_TYPE_PRODUCT -> 1
                        ShoppingAdapter.VIEW_TYPE_LOAD_MORE -> 2
                        else -> 1
                    }
                }
            }
        binding.rvProductList.layoutManager = layoutManager
    }

    private fun setUpRecentlyViewedProductRecyclerView() {
        recentlyProductAdapter = RecentlyProductAdapter(actionHandler = viewModel)
        binding.rvRecentlyViewed.adapter = recentlyProductAdapter
    }

    private fun observeViewModel() {
        observeShoppingProductItems()
        observeRecentlyViewedProductItems()
        observeProductUpdate()
        observeProductClick()
    }

    private fun observeShoppingProductItems() {
        viewModel.productItemsState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showShoppingProductData(state.data)
                is UIState.Empty -> showMessage(getString(R.string.empty_product_list))
                is UIState.Error ->
                    showMessage(state.exception.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun observeProductUpdate() {
        viewModel.updatedProduct.observe(this) { state ->
            when (state) {
                is UIState.Success -> shoppingAdapter.updateItem(state.data)
                is UIState.Empty -> return@observe
                is UIState.Error ->
                    showMessage(
                        state.exception.message ?: getString(R.string.unknown_error),
                    )
            }
        }
    }

    private fun observeRecentlyViewedProductItems() {
        viewModel.recentlyViewedProductsState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showRecentlyViewedProductData(state.data)
                is UIState.Empty -> return@observe
                is UIState.Error ->
                    showMessage(state.exception.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun observeProductClick() {
        viewModel.navigateToProductDetail.observe(this) { productId ->
            navigateToDetail(productId)
        }
    }

    private fun showShoppingProductData(data: List<ProductWithQuantity>) {
        shoppingAdapter.submitList(data)
    }

    private fun showRecentlyViewedProductData(data: List<RecentlyViewedProduct>) {
        recentlyProductAdapter.loadData(data)
    }

    private fun navigateToShoppingCart() {
        val intent = CartActivity.createIntent(this)
        activityResultLauncher.launch(intent)
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.loadRecentlyViewedProducts()
            if (result.resultCode == RESULT_OK) {
                val modifiedProductIds =
                    result.data?.getLongArrayExtra(EXTRA_KEY_MODIFIED_PRODUCT_IDS)?.toList()
                        ?: emptyList()
                if (modifiedProductIds.isNotEmpty()) {
                    viewModel.updateModifiedProducts(modifiedProductIds)
                }
            }
        }

    private fun navigateToDetail(productId: Long) {
        val intent = DetailActivity.createIntent(this, productId)
        activityResultLauncher.launch(intent)
    }
}
