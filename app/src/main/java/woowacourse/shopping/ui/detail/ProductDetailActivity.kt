package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.model.data.RecentProductsImpl
import woowacourse.shopping.ui.CountButtonClickListener
import woowacourse.shopping.ui.detail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.ui.detail.viewmodel.ProductDetailViewModelFactory

class ProductDetailActivity :
    AppCompatActivity(),
    CartButtonClickListener,
    CountButtonClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    private var toast: Toast? = null
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(ProductsImpl, RecentProductsImpl)
    }
    private val productId by lazy { productId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        showProductDetail()
        setOnListener()
        observeErrorMessage()
    }

    override fun onClick() {
        viewModel.addProductToCart()
        toast?.cancel()
        toast = Toast.makeText(this, getString(R.string.add_cart_complete), Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun plusCount() {
        viewModel.plusCount()
    }

    override fun minusCount() {
        viewModel.minusCount()
    }

    private fun initToolbar() {
        binding.toolbarDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_close -> finish()
            }
            false
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun showProductDetail() {
        viewModel.loadProduct(productId)
    }

    private fun setOnListener() {
        binding.cartButtonClickListener = this
        binding.countButtonClickListener = this
    }

    private fun observeErrorMessage() {
        viewModel.errorMsg.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it.isNotEmpty()) {
                    toast = Toast.makeText(this, it, Toast.LENGTH_SHORT)
                    toast?.show()
                }
            }
        }
    }

    private fun productId() =
        intent.getLongExtra(
            ProductDetailKey.EXTRA_PRODUCT_KEY,
            EXTRA_DEFAULT_VALUE,
        )

    companion object {
        private const val EXTRA_DEFAULT_VALUE = -1L

        fun startActivity(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).run {
            putExtra(ProductDetailKey.EXTRA_PRODUCT_KEY, productId)
            context.startActivity(this)
        }
    }
}
