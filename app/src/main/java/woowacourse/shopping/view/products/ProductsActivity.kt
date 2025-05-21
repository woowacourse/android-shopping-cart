package woowacourse.shopping.view.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.viewmodel.products.ProductsViewModel

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        adapter =
            ProductsAdapter(
                object : ProductsClickListener {
                    override fun onClick(product: Product) {
                        val intent = ProductDetailActivity.newIntent(this@ProductsActivity, product)
                        startActivity(intent)
                    }
                },
                object : LoadMoreClickListener {
                    override fun onClick() {
                        viewModel.loadNextPage()
                    }
                },
            )

        viewModel.productsInShop.observe(this) { list ->
            adapter.updateProductsView(list)
        }

        binding.cartImageBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))

        binding.rvProducts.adapter = adapter
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvProducts.layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup =
                    object : SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = binding.rvProducts.adapter?.getItemViewType(position)
                            return when (viewType) {
                                R.layout.item_product -> 1
                                else -> 2
                            }
                        }
                    }
            }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ProductsActivity::class.java)

        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
    }
}
