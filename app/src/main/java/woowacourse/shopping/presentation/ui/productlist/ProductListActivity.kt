package woowacourse.shopping.presentation.ui.productlist

import android.view.Menu
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.databinding.ProductListMenuLayoutBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductBrowsingHistoryListAdapter
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapterManager
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListViewType
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActivity

class ProductListActivity : BaseActivity<ActivityProductListBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_product_list

    private val viewModel: ProductListViewModel by viewModels {
        shoppingApplication.productListViewModelFactory()
    }

    private val historyListAdapter: ProductBrowsingHistoryListAdapter by lazy {
        ProductBrowsingHistoryListAdapter(
            actionHandler = viewModel,
        )
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(
            historyListAdapter = historyListAdapter,
            actionHandler = viewModel,
        )
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initStartView() {
        initDataBinding()
        initAdapter()
        initObserve()
    }

    private fun initViewModel() {
        viewModel.initPage()
    }

    private fun initDataBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProductListActivity
        }
    }

    private fun initAdapter() {
        binding.rvProductList.adapter = productListAdapter
        binding.rvProductList.layoutManager =
            ProductListAdapterManager(this, productListAdapter, 2, ProductListViewType.Product.ordinal)
    }

    private fun initObserve() {
        viewModel.navigateAction.observeEvent(this) { navigateAction ->
            when (navigateAction) {
                is ProductListNavigateAction.NavigateToProductDetail ->
                    ProductDetailActivity.startActivity(
                        this,
                        navigateAction.productId,
                    )

                is ProductListNavigateAction.NavigateToShoppingCart ->
                    ShoppingCartActivity.startActivity(this)
            }
        }

        viewModel.historyUiState.observe(this) { state ->
            productListAdapter.updateHistoryListState(state)
        }

        viewModel.productListUiModel.observe(this) { state ->
            state?.let { pagingProductUiModel ->
                productListAdapter.updateProductList(pagingProductUiModel)
            }
        }

        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is MessageProvider.DefaultErrorMessage -> showToastMessage(message.getMessage(this))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.product_list_menu_items, menu)
        val menuItem = menu.findItem(R.id.menu_shopping_cart)
        val menuBinding = ProductListMenuLayoutBinding.inflate(layoutInflater)
        menuBinding.handler = viewModel
        menuBinding.vm = viewModel
        menuBinding.lifecycleOwner = this
        menuItem.setActionView(menuBinding.root)
        return true
    }
}
