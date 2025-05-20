package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl

class ShoppingCartActivity :
    AppCompatActivity(),
    ActivityBoilerPlateCode<ActivityShoppingCartBinding> by ActivityBoilerPlateCodeImpl(
        R.layout.activity_shopping_cart,
    ) {
    private val viewModel: ShoppingCartViewModel by viewModels { ShoppingCartViewModel.Factory }
    private val shoppingCartAdapter: ShoppingCartAdapter by lazy {
        ShoppingCartAdapter { product, page ->
            viewModel.removeProduct(product, page)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        setMenubar(binding.toolbar as Toolbar)
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@ShoppingCartActivity) { page ->
                shoppingCartAdapter.updateProducts(page)
            }
        }
        binding.apply {
            shoppingCartList.adapter = shoppingCartAdapter
            viewModel = this@ShoppingCartActivity.viewModel
            onPagination = ::onPagination
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onPagination(page: Int) {
        viewModel.requestProductsPage(page)
    }

    private fun setMenubar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = MENU_BAR_TAG
        }
    }

    companion object {
        private const val MENU_BAR_TAG = "Cart"

        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
