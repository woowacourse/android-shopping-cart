package woowacourse.shopping.view.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
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
            ProductsAdapter { product ->
                val intent =
                    Intent(this, ProductDetailActivity::class.java).apply {
                        putExtra("product", product)
                    }
                startActivity(intent)
            }

        viewModel.productsInShop.observe(this) { list ->
            adapter.submit(list)
        }

        binding.cartImageBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
        binding.rvProducts.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount: Int = adapter.itemCount
                    Log.d("TAG", "onScrolled: $totalItemCount")

                    if (lastVisibleItemPosition > totalItemCount - 1) {
                        binding.btnMore.visibility = VISIBLE
                        binding.btnMore.setOnClickListener {
                            it.visibility = GONE
                            viewModel.loadNextPage()
                        }
                    }
                }
            },
        )

        binding.rvProducts.adapter = adapter
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
    }
}
