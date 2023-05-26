package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDao
import woowacourse.shopping.data.cart.cache.CartCacheImpl
import woowacourse.shopping.data.cart.datasource.CartDataSourceImpl
import woowacourse.shopping.data.cart.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.shoppingcart.adapter.CartProductCountPickerListener
import woowacourse.shopping.shoppingcart.adapter.CartRecyclerAdapter

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var cartRecyclerAdapter: CartRecyclerAdapter
    private val presenter: CartPresenter by lazy {
        CartPresenter(
            view = this,
            cartRepository = CartRepositoryImpl(
                cartDataSource = CartDataSourceImpl(
                    cartCache = CartCacheImpl(
                        shoppingDao = ShoppingDao(this)
                    )
                )
            )
        )
    }
    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        startObserving()

        presenter.loadShoppingCartProducts()
        setUpCartView()
        setUpCartToolbar()
    }

    private fun startObserving() {
        presenter.showingProducts.observe(this) {
            refreshCartProductView(it)
        }
        presenter.totalPrice.observe(this) {
            setUpTextTotalPriceView(it)
        }
        presenter.currentPage.observe(this) {
            setUpTextPageNumber(it)
        }
    }

    private fun setUpCartToolbar() {
        setSupportActionBar(binding.toolbarShoppingCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUpCartView() {
        cartRecyclerAdapter = CartRecyclerAdapter(
            shoppingCartProducts = presenter.showingProducts.value ?: listOf(),
            cartProductCountPickerListener = getCartProductCountPicker(),
            onProductSelectingChanged = presenter::changeProductSelectedState,
            onShoppingCartProductRemoved = presenter::removeShoppingCartProduct,
        )

        with(binding) {
            recyclerViewCart.adapter = cartRecyclerAdapter
            buttonNextPage.setOnClickListener {
                presenter.moveToNextPage()
            }
            buttonPreviousPage.setOnClickListener {
                presenter.moveToPrevPage()
            }
            checkBoxTotalProducts.setOnCheckedChangeListener { _, isChecked ->
                presenter.changeProductsSelectedState(isChecked)
            }
        }
    }

    private fun getCartProductCountPicker() = object : CartProductCountPickerListener {

        override fun onPlus(id: Int) {
            presenter.plusShoppingCartProductCount(id)
        }

        override fun onMinus(id: Int) {
            presenter.minusShoppingCartProductCount(id)
        }
    }

    private fun setUpTextTotalPriceView(price: Int) {
        binding.textTotalPrice.text = price.toString()
    }

    private fun refreshCartProductView(products: List<CartProductUiModel>) {
        cartRecyclerAdapter.refreshItems(products)
    }

    private fun setUpTextPageNumber(pageNumber: Int) {
        binding.textPageNumber.text = pageNumber.toString()
    }

    override fun showMessageReachedEndPage() {
        Toast.makeText(this, getString(R.string.message_last_page), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getIntent(context: Context): Intent {

            return Intent(context, CartActivity::class.java)
        }
    }
}
