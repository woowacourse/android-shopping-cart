package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.ResultState

class CartActivity :
    AppCompatActivity(),
    CartPageClickListener,
    CartCounterClickListener {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels { CartViewModelFactory(applicationContext) }
    private val cartAdapter by lazy {
        CartAdapter(
            cartCounterClickListener = this,
            cartPageClickListener = this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initInsets()
        initAdapter()
        setupToolbar()
        observeViewModel()
    }

    private fun initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.clCart) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initAdapter() {
        binding.rvCartProduct.adapter = cartAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbCart)
        binding.tbCart.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    cartAdapter.submitList(result.data)
                }

                is ResultState.Failure -> {
                    showToast(R.string.cart_toast_load_fail)
                }
            }
        }

        viewModel.toastMessage.observe(this) { resId ->
            showToast(resId)
        }
    }

    private fun showToast(
        @StringRes messageResId: Int,
    ) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onClickMinus(id: Long) {
        viewModel.decreaseQuantity(id)
    }

    override fun onClickPlus(id: Long) {
        viewModel.increaseQuantity(id)
    }

    override fun onClickPrevious() {
        viewModel.changePage(false)
    }

    override fun onClickNext() {
        viewModel.changePage(true)
    }

    override fun onClickDelete(cartItem: CartItem) {
        viewModel.deleteProduct(cartItem)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
