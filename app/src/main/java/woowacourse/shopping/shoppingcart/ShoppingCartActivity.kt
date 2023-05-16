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

    override fun setUpShoppingCartView(
        products: List<ShoppingCartProductUiModel>,
        onRemoved: (id: Int) -> Unit,
        onAdded: () -> Unit,
        onProductCountPlus: (product: ShoppingCartProductUiModel) -> Unit,
        onProductCountMinus: (product: ShoppingCartProductUiModel) -> Unit,
        onTotalPriceChanged: (products: List<ShoppingCartProductUiModel>) -> Unit,
    ) {
        shoppingCartRecyclerAdapter = ShoppingCartRecyclerAdapter(
            products = products,
            onRemoved = onRemoved,
            onAdded = onAdded,
            onProductCountPlus = onProductCountPlus,
            onProductCountMinus = onProductCountMinus,
            onTotalPriceChanged = onTotalPriceChanged,
            onPageChanged = ::setUpTextPageNumber
        )

        with(binding) {
            recyclerViewCart.adapter = shoppingCartRecyclerAdapter
            buttonNextPage.setOnClickListener {
                // TODO : MVP아님
                shoppingCartRecyclerAdapter.moveToNextPage()
            }
            buttonPreviousPage.setOnClickListener {
                // TODO : MVP아님
                shoppingCartRecyclerAdapter.moveToPreviousPage()
            }
            checkBoxTotalProducts.setOnCheckedChangeListener { _, isChecked ->
                presenter.changeProductsSelectedState(isChecked)
            }
        }
    }

    override fun setUpTextTotalPriceView(price: Int) {
        binding.textTotalPrice.text = price.toString()
    }

    override fun showMoreShoppingCartProducts(products: List<ShoppingCartProductUiModel>) {
        if (products.isEmpty()) {
            return Toast.makeText(
                this,
                getString(R.string.message_last_page),
                Toast.LENGTH_SHORT
            ).show()
        }

        shoppingCartRecyclerAdapter.addItems(products = products)
    }

    override fun refreshShoppingCartProductView(product: ShoppingCartProductUiModel) {
        shoppingCartRecyclerAdapter.updateItem(product = product)
    }

    override fun refreshShoppingCartProductView(products: List<ShoppingCartProductUiModel>) {
        shoppingCartRecyclerAdapter.setItems(products = products)
    }

    private fun setUpTextPageNumber(pageNumber: Int) {
        binding.textPageNumber.text = pageNumber.toString()
    }

    companion object {

        fun getIntent(context: Context): Intent {

            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
