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
import woowacourse.shopping.ui.cart.adapter.CartListAdapter
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val presenter: CartPresenter by lazy {
        CartPresenter(this, CartRepositoryImpl(this, ProductRepositoryImpl))
    }
    private var page: Int = 1

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
            presenter::deleteCartItem,
            presenter::plusItemCount,
            presenter::minusItemCount,
            presenter::updateCheckbox,
        )
    }

    private fun initCartList() {
        presenter.loadCartItems(limit = PAGE_SIZE, page = (page - 1) * PAGE_SIZE)
    }

    private fun initPageControlField() {
        binding.tvCartPage.text = "$page"
        presenter.setPageButtons(PAGE_SIZE)
    }

    private fun initCartTotalItemControlField() {
        // TODO: 초기 가격 및 구매하기 버튼
    }

    override fun setPageButtonClickListener(maxOffset: Int) {
        updateButtonsEnabledState(maxOffset)

        binding.btnPageDown.setOnClickListener {
            if (page > 1) {
                page--
                updatePage()
            }
            updateButtonsEnabledState(maxOffset)
        }
        binding.btnPageUp.setOnClickListener {
            if (page < maxOffset) {
                page++
                updatePage()
            }
            updateButtonsEnabledState(maxOffset)
        }
    }

    override fun setCartItems(cartItems: List<CartUIState>) {
        (binding.rvCart.adapter as CartListAdapter).updateItems(cartItems)
    }

    override fun updateTotalPrice(price: Int) {
        binding.tvCartTotalPrice.text = getString(R.string.product_price).format(price)
    }

    override fun updatePage() {
        presenter.loadCartItems(PAGE_SIZE, page)
        binding.tvCartPage.text = "$page"
    }

    private fun updateButtonsEnabledState(maxOffset: Int) {
        binding.btnPageDown.isEnabled = page > 1
        binding.btnPageUp.isEnabled = page < maxOffset
    }

    companion object {
        private const val PAGE_SIZE = 5

        fun startActivity(context: Context) {
            Intent(context, CartActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}
