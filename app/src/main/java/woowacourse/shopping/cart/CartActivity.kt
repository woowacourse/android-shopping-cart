package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.common.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.common.data.database.dao.CartDao
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        initCartAdapter()
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
        isNextButtonEnabled: Boolean
    ) {
        cartAdapter.updateCartProducts(cartProducts, currentPage, isNextButtonEnabled)
    }

    override fun updateNavigationVisibility(visibility: Boolean) {
        cartAdapter.updateNavigationVisible(visibility)
    }

    private fun initBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.cartToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCartAdapter() {
        cartAdapter = CartAdapter(
            emptyList(),
            onCartItemRemoveButtonClick = { presenter.removeCartProduct(it) },
            onPreviousButtonClick = { presenter.goToPreviousPage() },
            onNextButtonClick = { presenter.goToNextPage() }
        )
        binding.cartProductList.adapter = cartAdapter
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = CartPresenter(
            this, cartDao = CartDao(db), sizePerPage = SIZE_PER_PAGE
        )
    }

    companion object {
        private const val SIZE_PER_PAGE = 5

        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
