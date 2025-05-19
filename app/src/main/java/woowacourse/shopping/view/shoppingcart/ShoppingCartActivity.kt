package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ShoppingCartActivityTemplate
import woowacourse.shopping.view.page.Page

class ShoppingCartActivity :
    ShoppingCartActivityTemplate<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart) {
    private val viewModel: ShoppingCartViewModel by viewModels()
    private val handler: ShoppingCartEventHandler by lazy {
        object : ShoppingCartEventHandler {
            override fun onProductRemove(product: Product) {
                viewModel.removeProduct(product)
            }

            override fun onPagination(page: Int) {
                viewModel.requestProductsPage(page)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMenubar(binding.toolbar as Toolbar)
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@ShoppingCartActivity) {
                    page ->
                updateRecyclerView(page)
            }
        }
        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity.handler)
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity.handler
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMenubar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = MENU_BAR_TAG
        }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        binding.rvShoppingCartList.adapter.apply {
            val adapter = this as ShoppingCartAdapter
            val previousCount = itemCount
            adapter.updateProducts(page.items)
            notifyItemRangeChanged(0, previousCount)
        }
    }

    companion object {
        private const val MENU_BAR_TAG = "Cart"

        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
