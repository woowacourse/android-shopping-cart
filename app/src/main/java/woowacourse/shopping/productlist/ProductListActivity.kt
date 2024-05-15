package woowacourse.shopping.productlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
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

        attachAdapter()
        showProducts()

        supportActionBar?.title = "Shopping"
    }

    private fun attachAdapter() {
        adapter = ProductListAdapter(this)
        binding.rcvProductList.adapter = adapter
    }

    private fun showProducts() {
        viewModel.loadProducts()
        viewModel.products.observe(this) { products ->
            adapter.submitList(products.map { it.toProductUiModel() })
        }
    }

    override fun onProductClicked(id: Long) {
        startActivity(ProductDetailActivity.newInstance(this, id))
    }
}
