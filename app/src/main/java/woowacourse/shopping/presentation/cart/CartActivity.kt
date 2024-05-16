package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.detail.DetailActivity

class CartActivity : AppCompatActivity(), CartClickListener {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()

        binding.lifecycleOwner = this

        adapter = CartAdapter(this)
        binding.recyclerView.adapter = adapter

        val factory = CartViewModelFactory(repository = CartRepositoryImpl())
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        viewModel.shoppingCart.observe(
            this,
            Observer { cartItems ->
                adapter.loadData(cartItems)
            },
        )
        binding.vmShoppingCart = viewModel
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarCart
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onItemClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    override fun onDeleteItemClick(itemId: Long) {
        viewModel.deleteItem(itemId)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
