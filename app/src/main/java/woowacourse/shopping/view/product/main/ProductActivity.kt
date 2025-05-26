package woowacourse.shopping.view.product.main

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
import woowacourse.shopping.view.product.detail.ProductDetailActivity
import woowacourse.shopping.view.product.main.adapter.GridSpacingProductDecoration
import woowacourse.shopping.view.product.main.adapter.ProductAdapter

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val productViewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            ::onShowMore,
            ::onAddCart,
            ::navigateToProductDetail,
            viewModel = productViewModel,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initDataBinding()
        bindData()
        bindingAdapterManager()
        bindingAdapterDecoration()
    }

    private fun bindingAdapterManager() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (productAdapter.getItemViewType(position)) {
                        0 -> 1
                        else -> 2
                    }
            }
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun bindingAdapterDecoration() {
        val spacingInPx = resources.getDimensionPixelSize(R.dimen.spacing_12dp)
        val edgeSpacingPx = resources.getDimensionPixelSize(R.dimen.spacing_20dp)
        binding.rvProducts.addItemDecoration(
            GridSpacingProductDecoration(2, spacingInPx, edgeSpacingPx),
        )
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initDataBinding() {
        binding.apply {
            lifecycleOwner = this@ProductActivity
            vm = productViewModel
            rvProducts.adapter = productAdapter
        }
    }

    private fun bindData() {
        productViewModel.products.observe(this) { products ->
            productAdapter.updateData(products)
        }
        productViewModel.recentlyViewedProducts.observe(this) {
            productViewModel.fetchData()
        }
        productViewModel.onNavigateToCartEvent.observe(this) { navigateToCart() }
    }

    private fun navigateToCart() {
        startActivity(CartActivity.newIntent(context = this))
    }

    private fun onAddCart(
        product: Product,
        position: Int,
    ) {
        productViewModel.addCart(product)
        productAdapter.notifyItemChanged(position)
    }

    private fun onShowMore(): Boolean {
        productViewModel.fetchData()
        return true
    }

    private fun navigateToProductDetail(product: Product) {
        startActivity(ProductDetailActivity.newIntent(context = this, product = product))
    }
}
