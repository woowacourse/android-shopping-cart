package woowacourse.shopping.presentation.ui.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActivity

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductListVIewModel by viewModels()

    private val adapter: ProductListAdapter by lazy { ProductListAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityProductListBinding.inflate(layoutInflater).apply {
                vm = viewModel
                lifecycleOwner = this@ProductListActivity
            }
        setContentView(binding.root)
        initAdapter()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvProductList.adapter = adapter
    }

    private fun initObserve() {
        viewModel.navigateAction.observe(this) { navigateAction ->
            when (navigateAction) {
                is ProductListNavigateAction.NavigateToProductDetail ->
                    ProductDetailActivity.startActivity(
                        this,
                        navigateAction.productId,
                    )
            }
        }
        viewModel.pagingProduct.observe(this) { pagingProduct ->
            adapter.updateProductList(pagingProduct.productList)
        }

        viewModel.message.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
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
