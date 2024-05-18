package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingAdapter
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingViewType

class ShoppingActivity : BindingActivity<ActivityShoppingBinding>(), ShoppingHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels { ViewModelFactory() }

    private val adapter: ShoppingAdapter = ShoppingAdapter(this)

    override fun initStartView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadInitialProductByPage()
        }
        initAdapter()
        observeErrorEventUpdates()
        observeProductUpdates()
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, GRIDLAYOUT_COL)
        layoutManager.spanSizeLookup = getSpanManager()
        binding.rvShopping.layoutManager = layoutManager
        binding.rvShopping.adapter = adapter
    }

    private fun getSpanManager() =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == ShoppingViewType.Product.value) {
                    ShoppingViewType.Product.span
                } else {
                    ShoppingViewType.LoadMore.span
                }
            }
        }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(this) {
            when (it) {
                true -> handleErrorState()
                false -> {}
            }
        }
    }

    private fun handleErrorState() {
        Toast.makeText(this, "아이템을 모두 불러왔습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun observeProductUpdates() {
        viewModel.products.observe(this) { state ->
            when (state) {
                is UiState.None -> {}
                is UiState.Success -> handleFinishState(state)
            }
        }
    }

    private fun handleFinishState(newProducts: UiState.Success<List<Product>>) {
        adapter.insertItemsAtPosition(viewModel.maxPosition, newProducts.data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        CartActivity.start(this)
        return true
    }

    override fun onProductClick(productId: Long) {
        ProductDetailActivity.start(this, productId)
    }

    override fun onLoadMoreClick() {
        viewModel.addProductByPage()
    }

    companion object {
        const val GRIDLAYOUT_COL = 2
    }
}
