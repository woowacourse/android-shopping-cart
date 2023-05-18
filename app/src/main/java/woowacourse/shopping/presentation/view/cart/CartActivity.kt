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
        presenter.loadCartItems()
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
        cartAdapter = CartAdapter(carts, presenter::updateProductCount, presenter::deleteCartItem)
        binding.rvCart.adapter = cartAdapter
    }

    override fun setEnableLeftButton(isEnabled: Boolean) {
        binding.btCartListPageLeft.isEnabled = isEnabled
    }

    override fun setEnableRightButton(isEnabled: Boolean) {
        binding.btCartListPageRight.isEnabled = isEnabled
    }

    private fun setLeftButtonClick() {
        binding.btCartListPageLeft.setOnClickListener {
            presenter.calculatePreviousPage()
            presenter.loadCartItems()
        }
    }

    private fun setRightButtonClick() {
        binding.btCartListPageRight.setOnClickListener {
            presenter.calculateNextPage()
            presenter.loadCartItems()
        }
    }

    override fun setPageCountView(page: Int) {
        binding.tvCartListPageCount.text = page.toString()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
