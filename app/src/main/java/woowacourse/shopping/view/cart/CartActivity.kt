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
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.view.cart.adatper.CartAdapter
import woowacourse.shopping.view.cart.adatper.CartAdapterEventHandler
import woowacourse.shopping.view.cart.vm.CartViewModel
import woowacourse.shopping.view.cart.vm.CartViewModelFactory

class CartActivity : AppCompatActivity(), CartAdapterEventHandler, CartScreenEventHandler {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels { CartViewModelFactory() }
    private val cartAdapter by lazy {
        CartAdapter(
            items = viewModel.products.value ?: emptyList(),
            handler = this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        with(binding) {
            lifecycleOwner = this@CartActivity
            eventHandler = this@CartActivity
            adapter = cartAdapter
            vm = viewModel
        }

        viewModel.loadCarts(viewModel.pageNo.value ?: 1, 5)

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
        viewModel.products.observe(this) { value ->
            cartAdapter.submitList(value)
        }

        viewModel.pageState.observe(this) { value ->
            with(binding) {
                pager.isVisible = value.pageVisibility
                buttonForward.isEnabled = value.nextPageEnabled
                buttonPrevious.isEnabled = value.previousPageEnabled
            }
        }
    }

    override fun onClickDeleteItem(id: Long) {
        viewModel.deleteProduct(id)
    }

    override fun onClickNextPage() {
        viewModel.addPage()
    }

    override fun onClickPreviousPage() {
        viewModel.subPage()
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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
