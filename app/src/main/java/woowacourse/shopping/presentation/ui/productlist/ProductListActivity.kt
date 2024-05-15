package woowacourse.shopping.presentation.ui.productlist

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActivity

class ProductListActivity : BindingActivity<ActivityProductListBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_product_list

    private val viewModel: ProductListViewModel by viewModels()

    private val adapter: ProductListAdapter by lazy { ProductListAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProductListActivity
        }
        initAdapter()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvProductList.adapter = adapter
    }

    private fun initObserve() {
        viewModel.navigateAction.observeEvent(this) { navigateAction ->
            when (navigateAction) {
                is ProductListNavigateAction.NavigateToProductDetail ->
                    ProductDetailActivity.startActivity(
                        this,
                        navigateAction.productId,
                    )
            }
        }

        viewModel.uiState.observe(this) { state ->
            state.pagingProduct?.let { pagingProduct ->
                adapter.updateProductList(pagingProduct.productList)
            }
        }

        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is ProductListMessage.DefaultErrorMessage -> showToastMessage(message.toString(this))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_shopping_card -> ShoppingCartActivity.startActivity(this)
        }
        return true
    }
}
