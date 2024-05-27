package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDatabase
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductAdapter
import woowacourse.shopping.ui.products.adapter.RecentProductAdapter
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModelFactory
import woowacourse.shopping.ui.utils.urlToImage

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private val viewModel: ProductContentsViewModel by viewModels {
        ProductContentsViewModelFactory(
            (application as ShoppingApplication).productRepository,
            RecentProductRepositoryImpl.get(RecentProductDatabase.database().recentProductDao()),
            CartRepositoryImpl.get(CartDatabase.database().cartDao()),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setProductAdapter()
        setRecentProductAdapter()
        initToolbar()
        observeProductItems()
        observeRecentProductItems()
        moveToProductDetailPage()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCartItems()
        viewModel.loadRecentProducts()
    }

    private fun initToolbar() {
        binding.ivCart.setOnClickListener {
            CartActivity.startActivity(this)
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_contents)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun setProductAdapter() {
        binding.rvProducts.itemAnimator = null
        productAdapter =
            ProductAdapter(viewModel)
        binding.rvProducts.adapter = productAdapter
    }

    private fun setRecentProductAdapter() {
        binding.rvRecentProducts.itemAnimator = null
        recentProductAdapter = RecentProductAdapter(viewModel)
        binding.rvRecentProducts.adapter = recentProductAdapter
    }

    private fun observeProductItems() {
        viewModel.productWithQuantity.observe(this) {
            productAdapter.submitList(it)
        }
    }

    private fun observeRecentProductItems() {
        viewModel.recentProducts.observe(this) {
            recentProductAdapter.submitList(it)
        }
    }

    private fun moveToProductDetailPage() {
        viewModel.productDetailId.observe(this) {
            ProductDetailActivity.startActivity(this, it, true)
        }
    }
}

@BindingAdapter("imageUrl")
fun ImageView.bindUrlToImage(imageUrl: String?) {
    urlToImage(context, imageUrl)
}

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
