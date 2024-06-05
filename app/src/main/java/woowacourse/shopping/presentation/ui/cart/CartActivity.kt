package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionActivity

class CartActivity : BindingActivity<ActivityCartBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val viewModel: CartViewModel by viewModels { ViewModelFactory() }
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun initStartView() {
        initTitle()
        binding.rvCarts.adapter = cartAdapter
        binding.cartActionHandler = viewModel
        binding.lifecycleOwner = this

        initData()
        initObserver()
        initBackPressed()
    }

    private fun initBackPressed() {
        onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Intent().apply {
                        putExtra(
                            ShoppingActionActivity.EXTRA_UPDATED_PRODUCT,
                            viewModel.updateUiModel,
                        )
                    }.run {
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }
            }
        // 뒤로 가기 콜백을 추가
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun initData() {
        viewModel.findProductByOffset()
    }

    private fun initObserver() {
        viewModel.carts.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> {
                    cartAdapter.submitList(it.data)
                    with(binding) {
                        layoutPage.isVisible = viewModel.maxOffset > 0
                        btnRight.isEnabled = viewModel.offSet < viewModel.maxOffset
                        btnLeft.isEnabled = viewModel.offSet > 0
                        tvPageCount.text = (viewModel.offSet + OFFSET_BASE).toString()
                    }
                }
            }
        }
        viewModel.errorHandler.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initTitle() {
        title = getString(R.string.cart_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val OFFSET_BASE = 1

        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
