package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication.Companion.cartDatabase
import woowacourse.shopping.ShoppingApplication.Companion.recentProductDatabase
import woowacourse.shopping.data.db.cart.CartRepositoryImpl
import woowacourse.shopping.data.db.recent.RecentProductRepositoryImpl
import woowacourse.shopping.data.db.shopping.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.state.UIState

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }
    private val isMostRecentProductClicked: Boolean by lazy {
        intent.getBooleanExtra(
            IS_MOST_RECENT_PRODUCT_CLICKED,
            DEFAULT_IS_MOST_RECENT_PRODUCT_CLICKED,
        )
    }
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            cartRepository = CartRepositoryImpl(cartDatabase),
            productRepository = ProductRepositoryImpl(),
            recentProductRepository = RecentProductRepositoryImpl(recentProductDatabase),
            productId = productId,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDataBinding()
        observeViewModel()
        viewModel.saveRecentProduct(isMostRecentProductClicked)
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.updateRecentProductVisible(isMostRecentProductClicked)
    }

    private fun setUpDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun observeViewModel() {
        viewModel.detailUiState.observe(this) { state ->
            if (state is UIState.Error) {
                showError(
                    state.exception.message ?: getString(R.string.unknown_error),
                )
            }
        }

        viewModel.navigateToCart.observe(this) {
            it.getContentIfNotHandled()?.let {
                putCartItem()
            }
        }

        viewModel.navigateToRecentDetail.observe(this) {
            it.getContentIfNotHandled()?.let {
                navigateToDetail()
            }
        }

        viewModel.isFinishButtonClicked.observe(this) {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun navigateToDetail() {
        val recentProduct = viewModel.mostRecentProduct.value ?: return
        startActivity(
            createIntent(
                this,
                recentProduct.productId,
                true,
            ),
        )
    }

    private fun putCartItem() {
        Toast.makeText(this, PUR_CART_MESSAGE, Toast.LENGTH_SHORT).show()
        startActivity(CartActivity.createIntent(context = this))
    }

    companion object {
        private const val PUR_CART_MESSAGE = "장바구니에 상품이 추가되었습니다!"
        const val PRODUCT_ID = "product_id"
        const val INVALID_PRODUCT_ID = -1L
        private const val IS_MOST_RECENT_PRODUCT_CLICKED = "is_most_recent_product_clicked"
        private const val DEFAULT_IS_MOST_RECENT_PRODUCT_CLICKED = false

        fun createIntent(
            context: Context,
            productId: Long,
            isMostRecentProductClicked: Boolean = DEFAULT_IS_MOST_RECENT_PRODUCT_CLICKED,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
                putExtra(IS_MOST_RECENT_PRODUCT_CLICKED, isMostRecentProductClicked)
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            }
        }
    }
}
