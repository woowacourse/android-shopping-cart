package woowacourse.shopping.presentation.ui.productlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter

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
        adapter.updateProductList(viewModel.productList.value!!)
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
    }
}
