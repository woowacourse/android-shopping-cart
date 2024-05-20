package woowacourse.shopping.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.cart.CartActivity

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModelFactory(
            intent.getLongExtra(PRODUCT_ID_KEY, PRODUCT_ID_DEFAULT_VALUE),
            ProductRepository.getInstance(),
            CartRepository.getInstance(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initializeView()
    }

    private fun initializeView() {
        initializeToolbar()
        initializeAddCartButton()
        initializeProductLoadError()
        viewModel.loadProduct()
    }

    private fun initializeToolbar() {
        binding.toolbarDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_exit -> finish()
            }
            false
        }
    }

    private fun initializeAddCartButton() {
        binding.btnProductDetailAddCart.setOnClickListener {
            viewModel.addCartProduct()
        }

        viewModel.isSuccessAddCart.observe(this) { isSuccessEvent ->
            val isSuccess = isSuccessEvent.getContentIfNotHandled() ?: return@observe
            if (isSuccess) {
                showAddCartSuccessDialog()
            } else {
                showAddCartFailureToast()
            }
        }
    }

    private fun showAddCartSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.add_cart_success_title))
            .setMessage(getString(R.string.add_cart_success))
            .setPositiveButton(getString(R.string.common_move)) { _, _ ->
                navigateToCartView()
            }
            .setNegativeButton(getString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun showAddCartFailureToast() {
        Toast.makeText(this, R.string.add_cart_failure, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun initializeProductLoadError() {
        viewModel.productLoadError.observe(this) { isErrorEvent ->
            val isError = isErrorEvent.getContentIfNotHandled() ?: return@observe
            if (!isError) return@observe
            showErrorSnackBar()
        }
    }

    private fun showErrorSnackBar() {
        Snackbar
            .make(binding.root, getString(R.string.common_error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.common_confirm)) { finish() }
            .show()
    }

    companion object {
        private const val PRODUCT_ID_KEY = "product_id_key"
        private const val PRODUCT_ID_DEFAULT_VALUE = -1L

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java)
                .putExtra(PRODUCT_ID_KEY, productId)
        }
    }
}
