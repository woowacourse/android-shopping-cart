package woowacourse.shopping.presentation.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.cart.CartRepositoryImp
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.adapter.CartAdapter

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding

    private lateinit var cartAdapter: CartAdapter

    private val presenter: CartContract.Presenter by lazy {
        CartPresenter(this, CartRepositoryImp(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setSupportActionBar()
        presenter.loadCartItems()
        binding.btCartListPageLeft.setOnClickListener {
            val currentCount = binding.tvCartListPageCount.text
            binding.tvCartListPageCount.text = (currentCount.toString().toInt() - 1).toString()
            presenter.updateCartItem(binding.tvCartListPageCount.text.toString().toInt())
        }
        binding.btCartListPageRight.setOnClickListener {
            val currentCount = binding.tvCartListPageCount.text
            binding.tvCartListPageCount.text = (currentCount.toString().toInt() + 1).toString()
            presenter.updateCartItem(binding.tvCartListPageCount.text.toString().toInt())
        }
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.toolbar_title_cart)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setCartItemsView(carts: List<CartModel>) {
        cartAdapter = CartAdapter(carts, ::deleteCartItem)
        binding.rvCart.adapter = cartAdapter
    }

    private fun deleteCartItem(position: Int) {
        presenter.deleteCartItem(position)
    }

    override fun updateCartItemView(carts: List<CartModel>) {
        cartAdapter = CartAdapter(carts, ::deleteCartItem)
        binding.rvCart.adapter = cartAdapter
    }

    override fun updateToDeleteCartItemView(position: Int) {
        cartAdapter.notifyItemRemoved(position)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
