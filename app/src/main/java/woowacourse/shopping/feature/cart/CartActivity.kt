package woowacourse.shopping.feature.cart

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.cart.adapter.CartViewHolder

class CartActivity :
    AppCompatActivity(),
    CartViewHolder.CartClickListener {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels {
        ViewModelFactory { CartViewModel(CartRepositoryImpl(CartDatabase.getDatabase(this))) }
    }
    private val adapter: CartAdapter by lazy { CartAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.rvGoods.adapter = adapter
        binding.viewModel = viewModel

        updatePageButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClickDeleteButton(cart: Cart) {
        viewModel.delete(cart)
    }

    override fun insertToCart(cart: Cart) {
        viewModel.insertToCart(cart)
    }

    override fun removeFromCart(cart: Cart) {
        viewModel.removeFromCart(cart)
    }

    private fun updatePageButton() {
        viewModel.totalItemsCount.observe(this) {
            viewModel.updatePageButtonStates()
        }
        viewModel.page.observe(this) {
            viewModel.updatePageButtonStates()
        }
    }
}
