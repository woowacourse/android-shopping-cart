package woowacourse.shopping.presentation.ui.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.base.factory.ViewModelFactory
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.OrderListAdapter

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartBinding
    private val viewModel: ShoppingCartViewModel by viewModels { ViewModelFactory() }

    private val adapter: OrderListAdapter by lazy { OrderListAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.cart_title)
        }

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ShoppingCartActivity
        }
        initAdapter()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvOrderList.adapter = adapter
    }

    private fun initObserve() {
        viewModel.pagingOrder.observe(this) { orderList ->
            adapter.updateOrderList(orderList.orderList)
        }

        viewModel.message.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
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
