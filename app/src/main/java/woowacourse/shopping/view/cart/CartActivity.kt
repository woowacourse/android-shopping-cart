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
import woowacourse.shopping.App
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.view.cart.adapter.CartAdapter
import woowacourse.shopping.view.cart.vm.CartUiEvent
import woowacourse.shopping.view.cart.vm.CartViewModel
import woowacourse.shopping.view.cart.vm.CartViewModelFactory
import woowacourse.shopping.view.core.ext.showToast
import woowacourse.shopping.view.core.handler.CartQuantityHandler

class CartActivity : AppCompatActivity(), CartQuantityHandler, CartAdapter.Handler {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels {
        val container = (application as App).container
        CartViewModelFactory(
            container.cartRepository,
            container.productRepository,
        )
    }
    private val cartAdapter by lazy {
        CartAdapter(items = emptyList(), handler = this, cartQuantityHandler = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        viewModel.loadCarts()

        setUpBinding()
        setUpSystemBars()
        initView()
        observeViewModel()
    }

    private fun setUpBinding() {
        with(binding) {
            lifecycleOwner = this@CartActivity
            adapter = cartAdapter
            vm = viewModel
        }
    }

    private fun setUpSystemBars() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_bar_title_cart_screen)
    }

    private fun initView() {
        binding.recyclerViewCart.apply {
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { value ->
            cartAdapter.submitList(value.items)
        }

        viewModel.event.observe(this) { value ->
            when (value) {
                is CartUiEvent.ShowCannotIncrease ->
                    showToast(
                        getString(R.string.text_over_quantity)
                            .format(value.quantity),
                    )
            }
        }
    }

    override fun onClickDeleteItem(cardId: Long) {
        viewModel.deleteProduct(cardId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClickIncrease(productId: Long) {
        viewModel.increaseCartQuantity(productId)
    }

    override fun onClickDecrease(productId: Long) {
        viewModel.decreaseCartQuantity(productId)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
