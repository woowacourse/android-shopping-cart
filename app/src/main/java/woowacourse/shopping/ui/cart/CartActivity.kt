package woowacourse.shopping.ui.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartAdapter
import woowacourse.shopping.ui.products.ProductsActivity

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CartViewModel> {
        CartViewModelFactory(
            ProductRepository.getInstance(),
            CartRepository.getInstance(),
        )
    }
    private val adapter by lazy {
        CartAdapter(
            onClickExit = { viewModel.deleteCartItem(it) },
            onIncreaseProductQuantity = { viewModel.increaseQuantity(it) },
            onDecreaseProductQuantity = { viewModel.decreaseQuantity(it) },
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
        initializeCartAdapter()
        viewModel.changedCartEvent.observe(this) {
            it.getContentIfNotHandled() ?: return@observe
            setRequireActivityResult()
        }
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initializeCartAdapter() {
        binding.rvCart.itemAnimator = null
        binding.rvCart.adapter = adapter

        viewModel.productUiModels.observe(this) {
            adapter.updateCartItems(it)
        }
    }

    private fun setRequireActivityResult() {
        val resultIntent = Intent().putExtra(ProductsActivity.IS_CHANGED_CART_KEY, true)
        setResult(Activity.RESULT_OK, resultIntent)
    }
}
