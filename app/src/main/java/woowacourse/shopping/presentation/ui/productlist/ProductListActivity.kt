package woowacourse.shopping.presentation.ui.productlist

import android.view.Menu
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.data.repsoitory.DefaultHistoryRepository
import woowacourse.shopping.data.repsoitory.DefaultOrderRepository
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.databinding.ProductListMenuLayoutBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.HistoryListAdapter
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapterManager
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActivity

class ProductListActivity : BaseActivity<ActivityProductListBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_product_list

    private val viewModel: ProductListViewModel by viewModels {
        val localHistoryDataSource = shoppingApplication.localHistoryDataSource
        val localOrderDataSource = shoppingApplication.localOrderDataSource
        ProductListViewModelFactory(
            DummyProductList,
            DefaultOrderRepository(localOrderDataSource),
            DefaultHistoryRepository(localHistoryDataSource),
        )
    }

    private val historyListAdapter: HistoryListAdapter by lazy { HistoryListAdapter(actionHandler = viewModel) }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(
            historyListAdapter = historyListAdapter,
            actionHandler = viewModel,
        )
    }

    override fun onResume() {
        super.onResume()
        initPage()
    }

    override fun initStartView() {
        initDataBinding()
        initAdapter()
        initObserve()
    }

    private fun initPage() {
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
            ProductListAdapterManager(this, productListAdapter, 2, PRODUCT_VIEW_TYPE)
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

        viewModel.uiState.observe(this) { state ->
            state.pagingProductUiModel?.let { pagingProductUiModel ->
                productListAdapter.updateProductList(pagingProductUiModel)
            }
            state.histories?.let { histories ->
                historyListAdapter.updateHistoryList(histories)
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
