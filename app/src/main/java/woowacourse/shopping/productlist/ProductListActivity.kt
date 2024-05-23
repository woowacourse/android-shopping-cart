package woowacourse.shopping.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActionLayoutCartIconBinding
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.ViewModelFactory

class ProductListActivity : AppCompatActivity(), ProductListClickAction {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
    private val viewModel: ProductListViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        attachAdapter()
        showProducts()

        binding.btnLoadMoreProducts.setOnClickListener {
            viewModel.loadProducts(adapter.itemCount)
        }

        setupToolBar()
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.toolbarProductList as Toolbar)
        supportActionBar?.title = getString(R.string.action_bar_title_product_list_activity)
    }

    private fun attachAdapter() {
        adapter = ProductListAdapter(this)
        binding.rcvProductList.adapter = adapter
    }

    private fun showProducts() {
        viewModel.productUiModels.observe(this) { products ->
            adapter.submitList(products)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        val menuBinding: ActionLayoutCartIconBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.action_layout_cart_icon,null, false)
        val menuItem = menu?.findItem(R.id.menu_shopping_cart_nav)

        menuItem?.actionView = menuBinding.root

        menuBinding.root.setOnClickListener {
            if (menuItem != null) {
                onOptionsItemSelected(menuItem)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_shopping_cart_nav -> startActivity(ShoppingCartActivity.newInstance(this))
            else -> {}
        }
        return true
    }

    override fun onProductClicked(id: Long) {
        startActivity(ProductDetailActivity.newInstance(this, id))
    }
}
