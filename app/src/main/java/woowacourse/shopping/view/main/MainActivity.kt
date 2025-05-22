package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl
import woowacourse.shopping.view.main.adapter.ProductsAdapter
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import kotlin.getValue

class MainActivity :
    AppCompatActivity(),
    ActivityBoilerPlateCode<ActivityMainBinding> by ActivityBoilerPlateCodeImpl(
        R.layout.activity_main,
    ) {
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(viewModel.totalShoppingCartSize)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setSupportActionBar(binding.toolbar as Toolbar)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            onLoadMoreProducts = ::onLoadMoreProducts
        }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        (binding.toolbar as Toolbar).menu.findItem(R.id.menu_item_shopping_cart).actionView?.let {
            val badge = it.findViewById<TextView>(R.id.shopping_cart_alarm_badge)
            viewModel.totalShoppingCartSize.observe(this) { it ->
                badge.text = it.toString()
                badge.visibility =
                    if (badge.text.toString().toInt() > 0) View.VISIBLE else View.GONE
            }
            it.setOnClickListener {
                viewModel.saveCurrentShoppingCart(productsAdapter.quantity)
                val intent = ShoppingCartActivity.newIntent(this)
                startActivity(intent)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_shopping_cart) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoadMoreProducts(page: Int) {
        binding.btnLoadMoreProducts.visibility = View.GONE
        viewModel.requestProductsPage(page)
    }

    private fun initRecyclerview() {
        viewModel.updateShoppingCartSize()
        binding.productList.apply {
            adapter = productsAdapter
            addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        }
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@MainActivity) { mainRecyclerViewProduct ->
                productsAdapter.updateProducts(mainRecyclerViewProduct)
            }
        }
    }
}

private class ProductsOnScrollListener(
    private val binding: ActivityMainBinding,
    private val viewModel: ProductsViewModel,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        binding.btnLoadMoreProducts.visibility =
            if (
                lastVisibleItemPosition == layoutManager.itemCount - 1 &&
                viewModel.totalSize > (recyclerView.adapter?.itemCount ?: 0)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
