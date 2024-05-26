package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.app.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.factory(
            (application as ShoppingApplication).productRepository,
            (application as ShoppingApplication).shoppingCartRepository,
            (application as ShoppingApplication).productHistoryRepository,
        )
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    override fun initCreateView() {
        initActionBar()
        initDataBinding()
        initObserve()
    }

    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.product_detail_title)
    }

    private fun initDataBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProductDetailActivity
            productCountHandler = viewModel
        }
    }

    private fun initObserve() {
        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is MessageProvider.DefaultErrorMessage -> showToastMessage(message.getMessage(this))

                is ProductDetailMessage.NoSuchElementErrorMessage ->
                    showToastMessage(message.getMessage(this))

                is ProductDetailMessage.AddToCartSuccessMessage ->
                    showSnackbar(message.getMessage(this)) {
                        anchorView = binding.tvAddToCart
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_detai_closed -> finish()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProduct()
    }

    companion object {
        const val PUT_EXTRA_PRODUCT_ID = "product_id"

        fun getIntent(
            context: Context,
            id: Long,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PUT_EXTRA_PRODUCT_ID, id)
            }
        }
    }
}
