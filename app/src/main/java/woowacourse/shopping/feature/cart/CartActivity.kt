package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.local.CartRepositoryImpl
import woowacourse.shopping.data.sql.cart.CartDao
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductUiModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartProductAdapter: CartProductAdapter

    private val cartProductClickListener: CartProductClickListener by lazy {
        object : CartProductClickListener {
            override fun onDeleteClick(cartId: Long) {
                presenter.handleDeleteCartProductClick(cartId)
            }

            override fun onCartCountChanged(cartId: Long, count: Int) {
                presenter.handleCartProductCartCountChange(cartId, count)
            }

            override fun onSelectedPurchaseChanged(cartId: Long, checked: Boolean) {
                presenter.handlePurchaseSelectedCheckedChange(cartId, checked)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        cartProductAdapter = CartProductAdapter(cartProductClickListener)
        binding.cartItemRecyclerview.adapter = cartProductAdapter
        presenter = CartPresenter(this, CartRepositoryImpl(CartDao(this)))
        presenter.loadInitCartProduct()
        binding.presenter = presenter

        supportActionBar?.title = getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setRecyclerViewAnimator()
    }

    private fun setRecyclerViewAnimator() {
        val animator = binding.cartItemRecyclerview.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    override fun updateCartProducts(newItems: List<CartProductUiModel>) {
        cartProductAdapter.setItems(newItems)
    }

    override fun setPreviousButtonState(enabled: Boolean) {
        binding.previousPageBtn.isEnabled = enabled
    }

    override fun setNextButtonState(enabled: Boolean) {
        binding.nextPageBtn.isEnabled = enabled
    }

    override fun setPageCount(count: Int) {
        binding.pageCountTextView.text = count.toString()
    }

    override fun setAllCheckedButtonState(isAllChecked: Boolean) {
        binding.allCheckView.setOnCheckedChangeListener { _, _ -> }
        binding.allCheckView.isChecked = isAllChecked
        binding.allCheckView.setOnCheckedChangeListener { _, isChecked ->
            presenter.handleCurrentPageAllCheckedChange(isChecked)
        }
    }

    override fun setOrderButtonState(enabled: Boolean, orderCount: Int) {
        binding.orderCount = orderCount
        binding.orderConfirmView.isEnabled = enabled
    }

    override fun setTotalMoney(money: String) {
        binding.money = money
    }

    override fun exitCartScreen() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.exit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_PAGE_KEY, presenter.page.currentPage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY)
        presenter.setPage(currentPage)
    }

    companion object {
        private const val CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY"

        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
