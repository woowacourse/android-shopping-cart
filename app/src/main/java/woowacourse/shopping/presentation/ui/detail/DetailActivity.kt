package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.view.Menu
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.ui.cart.CartActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            (application as ShoppingApplication).shoppingItemsRepository,
            (application as ShoppingApplication).cartRepository,
            (application as ShoppingApplication).recentlyViewedProductsRepository,
            productId = productId,
        )
    }

    override fun onCreateSetup() {
        binding.viewModel = viewModel
        setUpToolbar()
        observeViewModel()
    }

    private fun setUpToolbar() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.addToCart.observe(this) {
            showMessage(getString(R.string.add_to_cart))
            navigateToBack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun navigateToBack() {
        val modifiedProductIds = setOf(productId)
        val resultIntent =
            Intent().apply {
                putExtra(
                    CartActivity.EXTRA_KEY_MODIFIED_PRODUCT_IDS,
                    modifiedProductIds.toLongArray(),
                )
            }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val PRODUCT_ID = "product_id"
        const val INVALID_PRODUCT_ID = -1L

        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
        }
    }
}
