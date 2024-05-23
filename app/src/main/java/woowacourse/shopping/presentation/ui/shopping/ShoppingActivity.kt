package woowacourse.shopping.presentation.ui.shopping

import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.DetailActivity
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingAdapter

class ShoppingActivity : BaseActivity<ActivityShoppingBinding>(R.layout.activity_shopping) {
    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(
            ShoppingItemsRepositoryImpl(
                (application as ShoppingApplication).shoppingDatabase,
                (application as ShoppingApplication).cartDatabase,
            ),
            CartRepositoryImpl((application as ShoppingApplication).cartDatabase),
        )
    }
    private lateinit var adapter: ShoppingAdapter

    override fun onCreateSetup() {
        initializeViews()
        observeViewModel()
    }

    private fun initializeViews() {
        setUpToolbar()
        setUpRecyclerView()
        binding.viewModel = viewModel
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarMain
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener {
            navigateToShoppingCart()
            true
        }
    }

    private fun setUpRecyclerView() {
        adapter = ShoppingAdapter(viewModel)
        binding.rvProductList.adapter = adapter

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        ShoppingAdapter.VIEW_TYPE_PRODUCT -> 1
                        ShoppingAdapter.VIEW_TYPE_LOAD_MORE -> 2
                        else -> 1
                    }
                }
            }
        binding.rvProductList.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        viewModel.productItemsState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showData(state.data)
                is UIState.Empty -> showErrorMessage(getString(R.string.empty_product_list))
                is UIState.Error ->
                    showErrorMessage(state.exception.message ?: getString(R.string.unknown_error))
            }
        }

        viewModel.updatedProduct.observe(this) { state ->
            when (state) {
                is UIState.Success -> adapter.updateItem(state.data)
                is UIState.Empty -> return@observe
                is UIState.Error -> showErrorMessage(state.exception.message ?: getString(R.string.unknown_error))
            }
        }

        viewModel.navigateToProductDetail.observe(this) { productId ->
            onProductClick(productId)
        }
    }

    private fun showData(data: List<ProductWithQuantity>) {
        adapter.submitList(data)
    }

    private fun navigateToShoppingCart() {
        startActivity(CartActivity.createIntent(context = this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun onProductClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }
}
