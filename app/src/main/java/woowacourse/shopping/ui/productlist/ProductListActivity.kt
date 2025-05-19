package woowacourse.shopping.ui.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductListViewModel by viewModels { ProductListViewModel.Factory }
    private val productListAdapter: ProductListAdapter by lazy { initAdapter() }
    private val customGridLayoutManager: GridLayoutManager by lazy { initCustomGridLayoutManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        applyWindowInsets()
        setOnBackPressedCallback()

        initViews()
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(CartActivity.newIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitDialog()
                }
            },
        )
    }

    private fun showExitDialog() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.dialog_exit_title))
            .setMessage(getString(R.string.dialog_exit_message))
            .setPositiveButton(getString(R.string.dialog_exit_positive)) { _, _ ->
                finish()
            }
            .setNegativeButton(getString(R.string.dialog_exit_negative)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false)
            .show()
    }

    private fun initViews() {
        initAppbar()
        initRecyclerView()
    }

    private fun initAppbar() {
        setSupportActionBar(binding.toolbarProductList)
    }

    private fun initRecyclerView() {
        binding.productsRecyclerView.apply {
            adapter = productListAdapter
            layoutManager = customGridLayoutManager
        }
    }

    private fun initAdapter(): ProductListAdapter {
        return ProductListAdapter(
            items = emptyList(),
            productClickListener = { product ->
                val intent = ProductDetailActivity.newIntent(this@ProductListActivity, product)
                startActivity(intent)
            },
            loadMoreClickListener = {
                viewModel.loadProducts()
            },
        )
    }

    private fun initCustomGridLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this@ProductListActivity, 2).apply {
            spanSizeLookup = CustomSpanSizeLookup(binding.productsRecyclerView.adapter!!)
        }
    }

    private fun initObserver() {
        viewModel.products.observe(this) {
            productListAdapter.update(it)
        }
    }
}
