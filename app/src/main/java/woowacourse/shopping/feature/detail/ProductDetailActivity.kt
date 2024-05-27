package woowacourse.shopping.feature.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R
import woowacourse.shopping.common.QuantityControlListener
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryDatabase
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryLocalRepository
import woowacourse.shopping.data.product.ProductClient
import woowacourse.shopping.data.product.ProductRemoteRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.feature.detail.viewmodel.ProductDetailViewModelFactory
import woowacourse.shopping.feature.main.MainActivity.Companion.LAST_VIEWED_PRODUCT_STATUS_KEY
import woowacourse.shopping.feature.main.MainActivity.Companion.PRODUCT_ID_KEY

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private val productDetailViewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(
            ProductRemoteRepository(ProductClient()),
            CartDummyRepository(CartDatabase.initialize(this).cartDao()),
            InquiryHistoryLocalRepository(
                InquiryHistoryDatabase.initialize(this).recentViewedProductDao(),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeBinding()
        initializeView()
        initializeQuantityController()
    }

    private fun initializeBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = productDetailViewModel
    }

    private fun initializeView() {
        initializeToolbar()
        initializeLastViewedProduct()
        observeAddProductToCart()
        showProduct()
    }

    private fun initializeToolbar() {
        binding.toolbarDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_exit -> navigateToMainView()
            }
            false
        }
    }

    private fun navigateToMainView() {
        val resultIntent = Intent()
        resultIntent.putExtra(LAST_VIEWED_PRODUCT_STATUS_KEY, true)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun initializeLastViewedProduct() {
        binding.layoutProductDetailLastViewedProduct.setOnClickListener {
            navigateToLastViewedProduct()
        }
    }

    private fun navigateToLastViewedProduct() {
        productDetailViewModel.lastViewedProduct.observe(this) { inquiryHistory ->
            val intent =
                Intent(this, ProductDetailActivity::class.java).putExtra(
                    PRODUCT_ID_KEY,
                    inquiryHistory.product.id,
                )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun observeAddProductToCart() {
        productDetailViewModel.isSuccessAddToCart.observe(this) { isSuccessAddToCart ->
            if (isSuccessAddToCart) showAddCartDialog()
        }
    }

    private fun showAddCartDialog() {
        AlertDialog.Builder(this).setTitle(getString(R.string.add_cart_done_title))
            .setMessage(getString(R.string.add_cart_done_content))
            .setPositiveButton(getString(R.string.common_move)) { _, _ ->
                navigateToCartView()
            }.setNegativeButton(getString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false).show()
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun productId(): Long = intent.getLongExtra(PRODUCT_ID_KEY, PRODUCT_ID_DEFAULT_VALUE)

    private fun showProduct() {
        runCatching {
            productDetailViewModel.loadProduct(productId())
        }.onFailure {
            showErrorSnackBar()
        }
    }

    private fun showErrorSnackBar() {
        Snackbar.make(binding.root, getString(R.string.common_error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.common_confirm)) {
                finish()
            }.show()
    }

    private fun initializeQuantityController() {
        binding.quantityControlListener =
            object : QuantityControlListener {
                override fun addProduct() {
                    productDetailViewModel.increaseQuantity()
                }

                override fun deleteProduct() {
                    productDetailViewModel.decreaseQuantity()
                }
            }
    }

    companion object {
        private const val PRODUCT_ID_DEFAULT_VALUE = -1L
    }
}
