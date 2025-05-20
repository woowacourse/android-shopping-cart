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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ResultState

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels { CartViewModelFactory(applicationContext) }
    private val cartProductAdapter by lazy { CartProductAdapter(::deleteProduct) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.vm = viewModel
        binding.clickHandler = viewModel
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
        binding.rvCartProduct.adapter = cartProductAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbCart)
        binding.tbCart.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
    }

    private fun observeViewModel() {
        viewModel.toastMessage.observe(this) { resId ->
            showToast(resId)
        }

        viewModel.products.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    cartProductAdapter.submitList(result.data)
                }

                is ResultState.Failure -> {
                    showToast(R.string.cart_toast_load_failure)
                }
            }
        }

        viewModel.deleteProduct.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    cartProductAdapter.removeItem(result.data)
                    showToast(R.string.cart_toast_delete_success)
                }

                is ResultState.Failure -> {
                    showToast(R.string.cart_toast_delete_failure)
                }
            }
        }
    }

    private fun deleteProduct(product: Product) {
        viewModel.deleteProduct(product)
    }

    private fun showToast(
        @StringRes messageResId: Int,
    ) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
