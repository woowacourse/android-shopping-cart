package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        viewModel.cartItems.observe(this) { cartItems ->
            adapter.replaceItems(cartItems)
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
//        viewModel.isDeleteSuccess.observe(this, EventObserver { isSuccess ->
//            if (isSuccess) {
//                adapter.deleteItemByProductId(id)
//            }
//        })
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, ShoppingCartActivity::class.java)
    }
}
