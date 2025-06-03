package woowacourse.shopping.ui.fashionlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.utils.ViewModelFactory

class FashionProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductListViewModel by viewModels {
        val app = application as ShoppingCartApplication
        ViewModelFactory.createCartViewModelFactory(
            app.productRepository,
            app.cartRepository,
            app.historyRepository
        )
    }
    private lateinit var fashionProductListAdapter: FashionProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        applyWindowInsets()
        initViews()
        initObserver()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbarProductList)

        val customLayoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup =
                    object : SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType =
                                binding.productsRecyclerView.adapter?.getItemViewType(position)
                            return when (viewType) {
                                R.layout.product_item -> 1
                                else -> 2
                            }
                        }
                    }
            }

        fashionProductListAdapter =
            FashionProductListAdapter(
                viewModel, productClickListener =
                    object : ProductClickListener {
                        override fun onClick(product: Product) {
                            val intent =
                                ProductDetailActivity.newIntent(
                                    this@FashionProductListActivity,
                                    product
                                )
                            startActivity(intent)
                        }
                    })

        binding.productsRecyclerView.apply {
            adapter = fashionProductListAdapter
            layoutManager = customLayoutManager
        }
    }

    private fun initObserver() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.productsUiState.observe(this) { uiStates ->
            fashionProductListAdapter.update(uiStates)
        }
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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FashionProductListActivity::class.java)
        }
    }
}
