package woowacourse.shopping.ui.cart

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartAdapter

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CartViewModel> {
        CartViewModelFactory(
            ProductRepository.getInstance(),
            CartRepository.getInstance(),
        )
    }
    private val adapter by lazy {
        CartAdapter(viewModel)
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
        initializeCartObserveEvent()
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
            adapter.submitList(it)
        }
    }

    private fun initializeCartObserveEvent() {
        viewModel.changedCartEvent.observe(this) {
            it.getContentIfNotHandled() ?: return@observe
            setResult(Activity.RESULT_OK)
        }

        viewModel.pageLoadError.observe(this) {
            it.getContentIfNotHandled() ?: return@observe
            Toast.makeText(this, R.string.load_page_error, Toast.LENGTH_SHORT).show()
        }
    }
}
