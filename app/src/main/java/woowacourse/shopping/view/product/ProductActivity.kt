package woowacourse.shopping.view.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            ::onShowMore,
            ::navigateToProductDetail,
        )
    }
    private val productViewModel: ProductViewModel by viewModels { ProductViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initView()
        bindData()
        bindingAdapterManager()
    }

    private fun bindingAdapterManager() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (productAdapter.getItemViewType(position)) {
                        0 -> 1
                        1 -> 2
                        else -> 2
                    }
            }
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun initView() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.apply {
            lifecycleOwner = this@ProductActivity
            onClick = ::navigateToCart
            rvProducts.adapter = productAdapter
        }
    }

    private fun bindData() {
        productViewModel.products.observe(this) { products ->
            productAdapter.updateData(products)
        }
        productViewModel.isShowMore.observe(this) {
        }
    }

    private fun navigateToCart() {
        startActivity(CartActivity.newIntent(context = this))
    }

    private fun onShowMore(): Boolean {
        productViewModel.changeShowState(true)
        productViewModel.fetchData()
        return true
    }

    private fun navigateToProductDetail(product: Product) {
        startActivity(ProductDetailActivity.newIntent(context = this, product = product))
    }
}
