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
import woowacourse.shopping.model.ShoppingCartProductUiModel

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {

    private lateinit var shoppingCartRecyclerAdapter: ShoppingCartRecyclerAdapter
    private val presenter: ShoppingCartContract.Presenter by lazy {
        ShoppingCartPresenter(
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

        setUpShoppingCartToolbar()
        presenter.loadShoppingCartProducts()
    }

    private fun setUpShoppingCartToolbar() {
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

    override fun setUpShoppingCartView(products: List<ShoppingCartProductUiModel>) {
        shoppingCartRecyclerAdapter = ShoppingCartRecyclerAdapter(
            shoppingCartProducts = products,
            shoppingCartProductCountPicker = getShoppingCartProductCountPickerImpl(),
            onShoppingCartProductRemoved = presenter::removeShoppingCartProduct,
            onTotalPriceChanged = presenter::calcTotalPrice,
        )

        with(binding) {
            recyclerViewCart.adapter = shoppingCartRecyclerAdapter
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

    private fun getShoppingCartProductCountPickerImpl() = object : ShoppingCartProductCountPicker {

        override fun onPlus(product: ShoppingCartProductUiModel) {
            presenter.plusShoppingCartProductCount(product)
        }

        override fun onMinus(product: ShoppingCartProductUiModel) {
            presenter.minusShoppingCartProductCount(product)
        }
    }

    override fun setUpTextTotalPriceView(price: Int) {
        binding.textTotalPrice.text = price.toString()
    }

    override fun refreshShoppingCartProductView(product: ShoppingCartProductUiModel) {
        shoppingCartRecyclerAdapter.removeItem(product = product)
    }

    override fun refreshShoppingCartProductView(products: List<ShoppingCartProductUiModel>) {
        shoppingCartRecyclerAdapter.refreshItems(products = products)
    }

    override fun setUpTextPageNumber(pageNumber: Int) {
        binding.textPageNumber.text = pageNumber.toString()
    }

    override fun showMessageReachedEndPage() {
        Toast.makeText(this, getString(R.string.message_last_page), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getIntent(context: Context): Intent {

            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
