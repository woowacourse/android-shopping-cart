package woowacourse.shopping.presentation.ui.shopping

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity

class ShoppingActivity : BaseActivity<ActivityShoppingBinding>(), ShoppingHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels()
    private val adapter: ShoppingAdapter = ShoppingAdapter(this)

    override fun initStartView() {
        binding.rvShopping.adapter = adapter
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
        binding.btnShowMore.setOnClickListener {
            viewModel.loadProductByOffset()
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
}
