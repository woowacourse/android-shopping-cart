package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductAdapter
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModelFactory
import woowacourse.shopping.ui.utils.urlToImage

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: ProductContentsViewModel by viewModels {
        ProductContentsViewModelFactory(ProductsImpl, CartsImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setProductAdapter()
        initToolbar()
        observeProductItems()
        loadItems()
        setOnRecyclerViewScrollListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCartItems()
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
        adapter =
            ProductAdapter { productId ->
                ProductDetailActivity.startActivity(this, productId)
            }
        binding.rvProducts.adapter = adapter
    }

    private fun loadItems() {
        viewModel.loadProducts()
    }

    private fun observeProductItems() {
        viewModel.products.observe(this) {
            adapter.setData(it)
        }
    }

    private fun setOnRecyclerViewScrollListener() {
        binding.rvProducts.addOnScrollListener(
            onScrollListener(),
        )
    }

    private fun onScrollListener() =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                super.onScrolled(recyclerView, dx, dy)
                binding.btnLoadMore.visibility =
                    if (isLastItemVisible(recyclerView)) View.VISIBLE else View.GONE
            }
        }

    private fun isLastItemVisible(recyclerView: RecyclerView) =
        (recyclerView.layoutManager as GridLayoutManager)
            .findLastCompletelyVisibleItemPosition() == adapterItemSize()

    private fun adapterItemSize() = adapter.itemCount - OFFSET

    companion object {
        private const val OFFSET = 1
    }
}

@BindingAdapter("imageUrl")
fun ImageView.bindUrlToImage(imageUrl: String?) {
    urlToImage(context, imageUrl)
}

@BindingAdapter("isVisible")
fun TextView.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
