package woowacourse.shopping.presentation.ui.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.repsoitory.DummyShoppingCart
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.OrderListAdapter

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var viewModel: ShoppingCartViewModel

    private val adapter: OrderListAdapter by lazy { OrderListAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.cart_title)
        }

        viewModel =
            ViewModelProvider(
                this,
                ShoppingCartViewModelFactory(DummyShoppingCart),
            )[ShoppingCartViewModel::class.java]

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ShoppingCartActivity
        }
        initAdapter()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvOrderList.adapter = adapter
        viewModel.orderList.value?.let { orderList ->
            adapter.updateOrderList(orderList)
        }
    }

    private fun initObserve() {
        viewModel.orderList.observe(this) { orderList ->
            adapter.updateOrderList(orderList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ShoppingCartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
