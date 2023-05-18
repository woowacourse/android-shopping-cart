package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.common.model.PageNavigatorModel
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.datasource.dao.CartDao
import woowacourse.shopping.data.repository.CartRepository
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
        initTotalCheckBox()
        initPresenter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCart(checkableCartProducts: List<CheckableCartProductModel>) {
        cartAdapter.updateCartProducts(checkableCartProducts)
    }

    override fun updateNavigator(pageNavigatorModel: PageNavigatorModel) {
        binding.pageNavigator = pageNavigatorModel
    }

    override fun updateTotalPrice(price: Int) {
        binding.cartCheckedPriceText.text = getString(R.string.product_price, price)
    }

    override fun updateOrderText(countOfCartProducts: Int) {
        binding.cartOrderButton.text = getString(R.string.cart_order_text, countOfCartProducts)
    }

    override fun updateTotalCheck(isTotalChecked: Boolean) {
        binding.cartTotalCheck.isChecked = isTotalChecked
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
        cartAdapter = CartAdapter(emptyList(), {
            presenter.deleteCartProduct(it)
        }, {
            presenter.minusCartProduct(it)
        }, {
            presenter.plusCartProduct(it)
        }, { checkableCartProduct, isChecked ->
            presenter.checkCartProduct(checkableCartProduct, isChecked)
        })

        binding.cartProductList.adapter = cartAdapter
    }

    private fun initNavigator() {
        binding.cartNavigatorPreviousButton.setOnClickListener {
            presenter.loadPreviousPage()
        }
        binding.cartNavigatorNextButton.setOnClickListener {
            presenter.loadNextPage()
        }
    }

    private fun initTotalCheckBox() {
        binding.cartTotalCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed)
                presenter.checkWholeCartProduct(isChecked)
        }
    }

    private fun initPresenter() {
        presenter = CartPresenter(
            this,
            cartRepository = CartRepository(CartDao(shoppingDBOpenHelper.writableDatabase)),
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
