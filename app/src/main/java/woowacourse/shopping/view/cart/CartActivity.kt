package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.cart.adatper.CartAdapter
import woowacourse.shopping.view.cart.event.CartScreenEventHandler
import woowacourse.shopping.view.cart.vm.CartViewModel
import woowacourse.shopping.view.cart.vm.CartViewModelFactory
import woowacourse.shopping.view.main.MainActivity
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory()
    }
    private val cartAdapter by lazy {
        CartAdapter(quantitySelectorEventHandler = quantitySelectorEventHandler) { id ->
            viewModel.deleteProduct(id)
        }
    }

    private val quantitySelectorEventHandler
        get() =
            object : QuantitySelectorEventHandler {
                override fun onQuantityMinus(cart: Cart) {
                    viewModel.minusCartQuantity(cart)
                }

                override fun onQuantityPlus(cart: Cart) {
                    viewModel.plusCartQuantity(cart)
                }
            }

    private val cartScreenEvent
        get() =
            object : CartScreenEventHandler {
                override fun onClickNextPage() {
                    viewModel.addPage()
                }

                override fun onClickPreviousPage() {
                    viewModel.subPage()
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        with(binding) {
            lifecycleOwner = this@CartActivity
            eventHandler = cartScreenEvent
            adapter = cartAdapter
            vm = viewModel
        }

        viewModel.loadCarts()

        initView()
        observeViewModel()
    }

    private fun initView() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_bar_title_cart_screen)

        binding.recyclerViewCart.apply {
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.carts.observe(this) { value ->
            cartAdapter.submitList(value)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateToMain()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToMain() {
        val intent =
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        startActivity(intent)
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
