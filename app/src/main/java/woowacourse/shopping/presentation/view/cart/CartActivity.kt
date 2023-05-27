package woowacourse.shopping.presentation.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.view.cart.adapter.CartAdapter

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding

    private lateinit var cartAdapter: CartAdapter

    private val presenter: CartContract.Presenter by lazy {
        CartPresenter(
            this,
            CartRepositoryImpl(CartDao(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setSupportActionBar()
        presenter.loadCartItems()
        setLeftButtonClick()
        setRightButtonClick()
        observeTotalPrice()
    }
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1000) {
            }
        }
    }

    private fun observeTotalPrice() {
        presenter.totalPrice.observe(this) {
            binding.tvCartTotalPrice.text = getString(R.string.product_price_format, it)
        }
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.toolbar_title_cart)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setCartItemsView(carts: List<CartProductModel>) {
        cartAdapter = CartAdapter(
            carts, ::deleteCartItem, ::changeCartSelectedStatus, ::onProductCountChanged
        )
        binding.rvCart.adapter = cartAdapter
        setCheckAllChangeListener(carts)
    }

    private fun setCheckAllChangeListener(carts: List<CartProductModel>) {
        binding.checkboxCartAll.setOnClickListener {
            presenter.changeAllCartSelectedStatus(
                carts.map { it.product.id },
                binding.checkboxCartAll.isChecked
            )
            cartAdapter.updateItemsChecked(binding.checkboxCartAll.isChecked)
        }
    }

    override fun setCurrentPage(currentPage: Int) {
        binding.tvCartListPageCount.text = formatPage(currentPage)
    }

    private fun deleteCartItem(itemId: Long, itemCount: Int) {
        if (itemCount == 1) {
            binding.checkboxCartAll.isChecked = false
        }
        presenter.deleteCartItem(itemId)
    }

    private fun onProductCountChanged(productId: Long, productCount: Int) {
        presenter.updateProductCount(productId, productCount)
    }

    private fun changeCartSelectedStatus(
        productId: Long,
        isSelected: Boolean,
        isCartsChecked: List<Boolean>
    ) {
        if (isCartsChecked.all { it }) {
            binding.checkboxCartAll.isChecked = isSelected
        } else {
            binding.checkboxCartAll.isChecked = false
        }
        presenter.changeCartSelectedStatus(productId, isSelected)
    }

    override fun setEnableLeftButton(isEnabled: Boolean) {
        binding.btCartListPageLeft.isEnabled = isEnabled
    }

    override fun setEnableRightButton(isEnabled: Boolean) {
        binding.btCartListPageRight.isEnabled = isEnabled
    }

    override fun updateTotalPrice(totalPrice: Int) {
        binding.tvCartTotalPrice.text = getString(R.string.product_price_format, totalPrice)
    }

    private fun setLeftButtonClick() {
        binding.btCartListPageLeft.setOnClickListener {
            presenter.decrementPage()
        }
    }

    private fun setRightButtonClick() {
        binding.btCartListPageRight.setOnClickListener {
            presenter.incrementPage()
        }
    }

    private fun formatPage(currentPage: Int): String = currentPage.inc().toString()

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
