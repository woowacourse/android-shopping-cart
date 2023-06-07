package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.listener.CartItemListener
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val presenter: CartPresenter by lazy {
        CartPresenter(this, CartRepositoryImpl(this, ProductRepositoryImpl))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navigationIcon = binding.toolbarCart.navigationIcon?.mutate()
        DrawableCompat.setTint(
            navigationIcon!!,
            ContextCompat.getColor(this, android.R.color.white),
        )
        binding.toolbarCart.navigationIcon = navigationIcon
    }

    private fun initView() {
        initCartAdapter()
        initCartList()
        initPageControlField()
        initCartTotalItemControlField()
    }

    private fun initCartAdapter() {
        binding.rvCart.adapter = CartListAdapter(
            mutableListOf<CartUIState>(),
            object : CartItemListener {
                override fun onCloseButtonClick(productId: Long) {
                    presenter.deleteCartItem(productId)
                }

                override fun onPlusCountButtonClick(productId: Long, oldCount: Int) {
                    presenter.plusItemCount(productId, oldCount)
                }

                override fun onMinusCountButtonClick(productId: Long, oldCount: Int) {
                    presenter.minusItemCount(productId, oldCount)
                }

                override fun onCheckboxClick(isChecked: Boolean, item: CartUIState) {
                    presenter.updateCheckbox(isChecked, item)
                }
            },
        )
    }

    private fun initCartList() {
        presenter.loadCartItemsOfCurrentPage()
    }

    private fun initPageControlField() {
        binding.tvCartPage.text = "1"
        presenter.setPageButtons()
    }

    private fun initCartTotalItemControlField() {
        binding.tvCartTotalPrice.text = getString(R.string.product_price).format(0)
        binding.btnCartPurchase.text = getString(R.string.button_purchase).format(0)
        setCartTotalCheckboxListener()
    }

    override fun initPageButtonClickListener() {
        binding.btnPageDown.setOnClickListener {
            presenter.goLeftPage()
        }
        binding.btnPageUp.setOnClickListener {
            presenter.goRightPage()
        }
    }

    override fun updateLeftButtonsEnabled(isEnabled: Boolean) {
        binding.btnPageDown.isEnabled = isEnabled
    }

    override fun updateRightButtonsEnabled(isEnabled: Boolean) {
        binding.btnPageUp.isEnabled = isEnabled
    }

    override fun setCartItems(cartItems: List<CartUIState>) {
        (binding.rvCart.adapter as CartListAdapter).updateItems(cartItems)
    }

    override fun updateTotalPrice(price: Int) {
        binding.tvCartTotalPrice.text = getString(R.string.product_price).format(price)
    }

    override fun updateTotalPurchaseButton(amount: Int) {
        binding.btnCartPurchase.text = getString(R.string.button_purchase).format(amount)
    }

    override fun updateTotalCheckbox(isAllChecked: Boolean) {
        binding.cbCartTotal.setOnCheckedChangeListener(null)
        binding.cbCartTotal.isChecked = isAllChecked
        setCartTotalCheckboxListener()
    }

    private fun setCartTotalCheckboxListener() {
        binding.cbCartTotal.setOnCheckedChangeListener { _, isChecked ->
            presenter.setTotalItemsStateAtOnce(isChecked)
        }
    }

    override fun updatePage(page: Int) {
        presenter.loadCartItemsOfCurrentPage()
        binding.tvCartPage.text = "$page"
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
