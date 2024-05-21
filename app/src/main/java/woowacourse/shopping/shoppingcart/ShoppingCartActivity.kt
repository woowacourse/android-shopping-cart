package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.util.ViewModelFactory

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartClickAction {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter

    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initShoppingCart()

        adapter = ShoppingCartAdapter(this)
        binding.rcvShoppingCart.adapter = adapter

        updateView()
    }

    private fun initShoppingCart() {
        viewModel.loadCartItems(DEFAULT_CURRENT_PAGE)
        viewModel.updatePageSize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_bar_title_shopping_cart_activity)
    }

    private fun updateView() {
        viewModel.cartItems.observe(this) { cartItems ->
            adapter.submitList(cartItems)
        }

        viewModel.currentPage.observe(this) { currentPage ->
            viewModel.loadCartItems(currentPage)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemRemoveBtnClicked(id: Long) {
        viewModel.deleteCartItem(id)
        viewModel.updatePageSize()
    }

    companion object {
        private const val DEFAULT_CURRENT_PAGE = 1

        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
