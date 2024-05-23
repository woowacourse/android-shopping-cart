package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.ui.cart.CartActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            cartRepository = CartRepositoryImpl(ShoppingApplication.getInstance().database),
            shoppingRepository = ShoppingItemsRepositoryImpl(),
            productId = productId,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()

        binding.lifecycleOwner = this

        observeViewModel()
    }

    private fun setUpViewModel() {
        binding.viewModel = viewModel
    }

    private fun observeViewModel() {
        viewModel.addCartItem.observe(this) {
            it.getContentIfNotHandled()?.let {
                viewModel.createShoppingCartItem()
                navigateToCart()
            }
        }

        viewModel.moveBack.observe(this) {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        }
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
