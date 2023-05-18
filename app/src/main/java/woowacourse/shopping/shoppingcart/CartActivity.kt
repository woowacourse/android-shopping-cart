package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var cartRecyclerAdapter: CartRecyclerAdapter
    private val presenter: CartContract.Presenter by lazy {
        CartPresenter(
            view = this,
            repository = ShoppingDBAdapter(
                shoppingDao = ShoppingDao(this)
            )
        )
    }
    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        setUpCartToolbar()
        presenter.loadShoppingCartProducts()
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

    override fun setUpCartView(
        products: List<CartProductUiModel>,
        currentPage: Int,
    ) {
        cartRecyclerAdapter = CartRecyclerAdapter(
            shoppingCartProducts = products,
            cartProductCountPickerListener = getCartProductCountPicker(),
            onProductSelectingChanged = presenter::changeProductSelectedState,
            onShoppingCartProductRemoved = presenter::removeShoppingCartProduct,
            onTotalPriceChanged = presenter::calcTotalPrice,
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
            textPageNumber.text = currentPage.toString()
        }
    }

    private fun getCartProductCountPicker() = object : CartProductCountPickerListener {

        override fun onPlus(product: CartProductUiModel) {
            presenter.plusShoppingCartProductCount(product)
        }

        override fun onMinus(product: CartProductUiModel) {
            presenter.minusShoppingCartProductCount(product)
        }
    }

    override fun setUpTextTotalPriceView(price: Int) {
        binding.textTotalPrice.text = price.toString()
    }

    override fun refreshCartProductView(products: List<CartProductUiModel>) {
        cartRecyclerAdapter.refreshItems(products)
    }

    override fun setUpTextPageNumber(pageNumber: Int) {
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
