package woowacourse.shopping.ui.cart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.model.CartProductModel
import woowacourse.shopping.ui.model.ShoppingProductModel
import woowacourse.shopping.ui.shopping.ShoppingActivity

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartAdapter: CartAdapter

    private val cartProductListener = object : CartProductListener {
        override fun onCartItemRemoveButtonClick(cartProductModel: CartProductModel) {
            presenter.removeCartProduct(cartProductModel)
        }

        override fun onCheckBoxClick(cartProductModel: CartProductModel) {
            presenter.reverseCartProductChecked(cartProductModel)
            presenter.updateAllChecked()
        }

        override fun onMinusAmountButtonClick(cartProductModel: CartProductModel) {
            presenter.decreaseCartProductAmount(cartProductModel)
        }

        override fun onPlusAmountButtonClick(cartProductModel: CartProductModel) {
            presenter.increaseCartProductAmount(cartProductModel)
        }
    }
    private val cartNavigationListener = object : CartNavigationListener {
        override fun onPreviousButtonClick() {
            presenter.goToPreviousPage()
        }

        override fun onNextButtonClick() {
            presenter.goToNextPage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.cart_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.cartProductList.itemAnimator = null

        initCartAdapter()

        setupCartProductAllCheckbox()

        initPresenter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCart(
        cartProducts: List<CartProductModel>,
        currentPage: Int,
        isLastPage: Boolean
    ) {
        cartAdapter.updateCartProducts(cartProducts, currentPage, isLastPage)
    }

    override fun updateNavigationVisibility(visibility: Boolean) {
        cartAdapter.updateNavigationVisible(visibility)
    }

    override fun updateCartTotalPrice(price: Int) {
        binding.cartTotalPrice.text = getString(R.string.product_price, price)
    }

    override fun updateCartTotalAmount(amount: Int) {
        binding.cartOrderButton.text = getString(R.string.order_button, amount)
    }

    override fun notifyProductsChanged(difference: List<ShoppingProductModel>, amountDifference: Int) {
        val intent = ShoppingActivity.createIntent(this, difference, amountDifference)
        setResult(Activity.RESULT_OK, intent)
    }

    override fun updateCartProduct(prev: CartProductModel, new: CartProductModel) {
        cartAdapter.updateCartProduct(prev, new)
    }

    override fun updateAllChecked(isAllChecked: Boolean) {
        binding.cartProductAllCheckbox.isChecked = isAllChecked
    }

    private fun initCartAdapter() {
        cartAdapter = CartAdapter(
            cartProductListener,
            cartNavigationListener
        )
        binding.cartProductList.adapter = cartAdapter
    }

    private fun setupCartProductAllCheckbox() {
        binding.cartProductAllCheckbox.setOnClickListener {
            presenter.changeAllChecked(binding.cartProductAllCheckbox.isChecked)
        }
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = CartPresenter(
            this,
            cartRepository = CartRepositoryImpl(CartDao(db)),
            sizePerPage = SIZE_PER_PAGE
        )
    }

    override fun finish() {
        presenter.checkProductsChanged()
        super.finish()
    }

    companion object {
        private const val SIZE_PER_PAGE = 5

        fun createIntent(context: Context): Intent {
            return Intent(
                context,
                CartActivity::class.java
            )
        }
    }
}
