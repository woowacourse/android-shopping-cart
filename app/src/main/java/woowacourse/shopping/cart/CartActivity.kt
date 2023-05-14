package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.cart_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initCartAdapter()

        initPresenter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCart(cartProducts: List<CartProductModel>, currentPage: Int, isLastPage: Boolean) {
        cartAdapter.updateCartProducts(cartProducts, currentPage, isLastPage)
    }

    override fun updateNavigationVisibility(visibility: Boolean) {
        cartAdapter.updateNavigationVisible(visibility)
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = CartPresenter(
            this, cartDao = CartDao(db), sizePerPage = SIZE_PER_PAGE
        )
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

    companion object {
        private const val SIZE_PER_PAGE = 5

        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
