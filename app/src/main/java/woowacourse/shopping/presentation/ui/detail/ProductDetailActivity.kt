package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    override fun initStartView(savedInstanceState: Bundle?) {
        initActionBarTitle()
        val id = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        if (id == -1L) finish()
        viewModel.loadById(id)
        observeErrorEventUpdates()
        observeProductsUpdates()
    }

    private fun initActionBarTitle() {
        title = getString(R.string.detail_title)
    }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(this) {
            when (it) {
                true -> showToast(getString(R.string.product_not_found))
                false -> {}
            }
        }
    }

    private fun observeProductsUpdates() {
        viewModel.products.observe(this) { state ->
            when (state) {
                is UiState.None -> {}
                is UiState.Success -> handleSuccessState(state.data)
            }
        }
    }

    private fun handleSuccessState(product: Product) {
        binding.product = product
        binding.tvAddCart.setOnClickListener {
            finish()
            viewModel.saveCartItem(product)
        }
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

        fun start(
            context: Context,
            productId: Long,
        ) {
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                context.startActivity(this)
            }
        }
    }
}
