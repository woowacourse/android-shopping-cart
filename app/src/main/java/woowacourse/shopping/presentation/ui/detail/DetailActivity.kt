package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.ui.cart.CartActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            cartRepository = CartRepositoryImpl((application as ShoppingApplication).cartDatabase),
            shoppingRepository =
                ShoppingItemsRepositoryImpl(
                    (application as ShoppingApplication).shoppingDatabase,
                    (application as ShoppingApplication).cartDatabase,
                ),
            productId = productId,
        )
    }

    override fun onCreateSetup() {
        binding.viewModel = viewModel
        setUpToolbar()
        observeViewModel()
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarDetail
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.navigateToShoppingCart.observe(this) {
            navigateToCart()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun navigateToCart() {
        startActivity(
            CartActivity.createIntent(context = this),
        )
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
