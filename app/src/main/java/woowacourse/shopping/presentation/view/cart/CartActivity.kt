package woowacourse.shopping.presentation.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.adapter.CartAdapter

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding

    private lateinit var cartAdapter: CartAdapter

    private val presenter: CartContract.Presenter by lazy {
        CartPresenter(this, CartRepositoryImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setSupportActionBar()
        presenter.loadCartItems(getPageCount())
        setLeftButtonClick()
        setRightButtonClick()
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

    private fun deleteCartItem(itemId: Long) {
        presenter.deleteCartItem(getPageCount(), itemId)
    }

    override fun setEnableLeftButton(isEnabled: Boolean) {
        binding.btCartListPageLeft.isEnabled = isEnabled
    }

    override fun setEnableRightButton(isEnabled: Boolean) {
        binding.btCartListPageRight.isEnabled = isEnabled
    }

    private fun setLeftButtonClick() {
        binding.btCartListPageLeft.setOnClickListener {
            val nextPage = getPageCount().dec()
            binding.tvCartListPageCount.text = nextPage.toString()
            presenter.loadCartItems(nextPage)
        }
    }

    private fun setRightButtonClick() {
        binding.btCartListPageRight.setOnClickListener {
            val nextPage = getPageCount().inc()
            binding.tvCartListPageCount.text = nextPage.toString()
            presenter.loadCartItems(nextPage)
        }
    }

    private fun getPageCount(): Int = binding.tvCartListPageCount.text.toString().toInt()

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
