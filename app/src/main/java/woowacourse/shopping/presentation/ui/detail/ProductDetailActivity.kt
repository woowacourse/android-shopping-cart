package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.EventObserver
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionActivity.Companion.EXTRA_UPDATED_PRODUCT

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    override fun initStartView() {
        if (intent.getBooleanExtra(EXTRA_OVERLAY, false)) binding.layoutRecent.isVisible = false

        binding.detailActionHandler = viewModel

        title = getString(R.string.detail_title)

        val id = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        if (id == -1L) finish()

        viewModel.findCartProductById(id)
        viewModel.findOneRecentProduct()
        viewModel.product.observe(this) { state ->
            when (state) {
                is UiState.None -> {}
                is UiState.Success -> {
                    binding.cartProduct = state.data
                }
            }
        }
        viewModel.recentProduct.observe(this) { state ->
            when (state) {
                is UiState.None -> {}
                is UiState.Success -> {
                    binding.recentProduct = state.data
                }
            }
        }

        viewModel.errorHandler.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.cartHandler.observe(
            this,
            EventObserver {
                intent.apply {
                    putExtra(
                        EXTRA_UPDATED_PRODUCT,
                        it,
                    )
                }.run {
                    setResult(RESULT_OK, this)
                    this@ProductDetailActivity.finish()
                }
            },
        )

        viewModel.navigateHandler.observe(
            this,
            EventObserver {
                ProductDetailActivity.createIntent(this, it).apply {
                    startActivity(this)
                }
            },
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
        const val EXTRA_OVERLAY = "overlay"

        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(EXTRA_PRODUCT_ID, productId)
                if (context is ProductDetailActivity) putExtra(EXTRA_OVERLAY, true)
            }
        }
    }
}
