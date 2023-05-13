package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.common.data.dao.CartDao
import woowacourse.shopping.common.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartAdapter: CartAdapter

    private val shoppingDBOpenHelper: ShoppingDBOpenHelper by lazy {
        ShoppingDBOpenHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        initCartAdapter()
        initNavigator()
        initPresenter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCart(
        cartProductsModel: List<CartProductModel>
    ) {
        cartAdapter.updateCartProducts(cartProductsModel)
    }

    override fun updateNavigator(currentPage: Int, maxPage: Int) {
        binding.cartNavigatorLayout.visibility = isNavigatorVisible(maxPage)
        binding.cartNavigatorPreviousButton.isEnabled = isFirstPage(currentPage)
        binding.cartNavigatorNextButton.isEnabled = isLastPage(currentPage, maxPage)
        binding.cartNavigatorPageText.text = currentPage.toString()
    }

    private fun isLastPage(currentPage: Int, maxPage: Int) = currentPage == maxPage - 1

    private fun isFirstPage(currentPage: Int) = currentPage != 1

    private fun isNavigatorVisible(maxPage: Int) = if (maxPage <= 1) View.GONE else View.VISIBLE

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
            onCartItemRemoveButtonClick = { presenter.removeCartProduct(it) }
        )
        binding.cartProductList.adapter = cartAdapter
    }

    private fun initNavigator() {
        binding.cartNavigatorPreviousButton.setOnClickListener {
            presenter.goToPreviousPage()
        }
        binding.cartNavigatorNextButton.setOnClickListener {
            presenter.goToNextPage()
        }
    }

    private fun initPresenter() {
        presenter = CartPresenter(
            this,
            cartDao = CartDao(shoppingDBOpenHelper.writableDatabase),
            countPerPage = SIZE_PER_PAGE
        )
    }

    companion object {
        private const val SIZE_PER_PAGE = 5

        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
