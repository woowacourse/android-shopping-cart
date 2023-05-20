package woowacourse.shopping.presentation.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.SimpleItemAnimator
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

    private val cartProductListener = object : CartProductListener {
        override fun onCheckChanged(cartId: Long, checked: Boolean) {
            presenter.updateProductChecked(cartId, checked)
            presenter.calculateTotalPrice()
        }

        override fun onCountClick(cartId: Long, count: Int) {
            presenter.updateProductCount(cartId, count)
            presenter.calculateTotalPrice()
        }

        override fun onDeleteClick(cartId: Long) {
            presenter.deleteCartItem(cartId)
            presenter.calculateTotalPrice()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setSupportActionBar()
        setRecyclerViewAnimator()
        presenter.loadCartItems()
        presenter.calculateTotalPrice()
        setLeftButtonClick()
        setRightButtonClick()
        setAllProduceCheckedClick()
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

    private fun setRecyclerViewAnimator() {
        val animator = binding.rvCart.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    override fun setCartItemsView(carts: List<CartModel>) {
        cartAdapter = CartAdapter(carts, cartProductListener)
        binding.rvCart.adapter = cartAdapter
    }

    override fun setChangedCartItemsView(carts: List<CartModel>) {
        cartAdapter.updateList(carts)
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

    private fun setAllProduceCheckedClick() {
        binding.cbCartAll.setOnCheckedChangeListener { _, isChecked ->
            presenter.updateCurrentPageAllProductChecked(isChecked)
            presenter.calculateTotalPrice()
        }
    }

    override fun updateAllChecking(startPosition: Int, count: Int) {
        cartAdapter.updateAllChecking(startPosition, count)
    }

    override fun setAllCartChecked(isChecked: Boolean) {
        binding.cbCartAll.setOnCheckedChangeListener { _, _ -> }
        binding.cbCartAll.isChecked = isChecked
        setAllProduceCheckedClick()
    }

    override fun setPageCountView(page: Int) {
        binding.tvCartListPageCount.text = page.toString()
    }

    override fun setTotalPriceView(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
