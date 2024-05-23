package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.home.ProductQuantity

class CartActivity : AppCompatActivity() {
    private val binding: ActivityCartBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_cart)
    }
    private val viewModel: CartViewModel by viewModels {
        val application = application as ShoppingApplication
        CartViewModelFactory(
            application.cartRepository,
            application.productRepository,
        )
    }
    private val adapter: CartAdapter by lazy {
        CartAdapter(viewModel, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBindingVariables()
        initializeToolbar()
        initializeOnBackPressedCallback()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navigateBackToMain()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun initializeBindingVariables() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.adapter = adapter
    }

    private fun initializeOnBackPressedCallback() {
        val onBackPressedCallBack =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = navigateBackToMain()
            }
        onBackPressedDispatcher.addCallback(onBackPressedCallBack)
    }

    private fun navigateBackToMain() {
        setResult(
            RESULT_OK,
            Intent().putExtra(
                "quantities",
                viewModel.alteredCartItems
            )
        )
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
