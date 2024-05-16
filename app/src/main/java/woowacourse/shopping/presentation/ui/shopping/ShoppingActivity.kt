package woowacourse.shopping.presentation.ui.shopping

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewHolder.ProductViewHolder.Companion.PRODUCT_VIEW_TYPE

class ShoppingActivity : BindingActivity<ActivityShoppingBinding>(), ShoppingHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels { ViewModelFactory() }

    private val adapter: ShoppingAdapter = ShoppingAdapter(this)

    override fun initStartView() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.getItemViewType(position) == PRODUCT_VIEW_TYPE) {
                        1
                    } else {
                        2
                    }
                }
            }
        binding.rvShopping.layoutManager = layoutManager
        binding.rvShopping.adapter = adapter

        viewModel.loadProductByOffset()

        viewModel.products.observe(this) {
            when (it) {
                is UiState.Finish -> {
                    adapter.updateList(it.data)
                }

                is UiState.Error -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }

                is UiState.None -> {
                    // TODO:초기 상태
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        CartActivity.start(this)
        return true
    }

    override fun onClick(productId: Long) {
        ProductDetailActivity.start(this, productId)
    }

    override fun loadMore() {
        viewModel.loadProductByOffset()
    }
}
