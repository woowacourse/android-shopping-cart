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
import woowacourse.shopping.util.showToastMessage

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartClickAction {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var adapter: ShoppingCartAdapter

    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        adapter = ShoppingCartAdapter(this)
        binding.rcvShoppingCart.adapter = adapter

        initShoppingCart()

        updateView()
    }

    private fun initShoppingCart() {
        viewModel.loadCartItems()
        viewModel.updatePageCount()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
    }

    private fun updateView() {
        viewModel.loadState.observe(this) { state ->
            when (state) {
                is LoadCartItemState.AddNextPageOfItem -> adapter.addItem(state.result)
                is LoadCartItemState.InitView -> adapter.replaceItems(state.currentCartItems.items)
                is LoadCartItemState.DeleteCartItem -> adapter.deleteItemByProductId(state.result)
                is LoadCartItemState.ChangeItemCount -> adapter.changeProductInfo(state.result)
                is LoadCartItemState.MinusFail -> showToastMessage(R.string.min_cart_item_message)
                is LoadCartItemState.PlusFail -> showToastMessage(R.string.max_cart_item_message)
            }
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
    }

    override fun onPlusCountClicked(id: Long) {
        viewModel.plusCartItemCount(id)
    }

    override fun onMinusCountClicked(id: Long) {
        viewModel.minusCartItemCount(id)
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
