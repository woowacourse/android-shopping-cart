package woowacourse.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.ProductUiModel

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
        products: List<ProductUiModel>,
        onRemoved: (id: Int) -> Unit,
    ) {
        shoppingCartRecyclerAdapter = ShoppingCartRecyclerAdapter(
            products = products,
            onRemoved = onRemoved,
            showingRule = ShowingShoppingCartProducts(),
            onPageChanged = ::setUpTextPageNumber
        )

        with(binding) {
            recyclerViewCart.adapter = shoppingCartRecyclerAdapter
            buttonNextPage.setOnClickListener {
                presenter.readMoreShoppingCartProducts()
            }
            buttonPreviousPage.setOnClickListener {
                shoppingCartRecyclerAdapter.moveToPreviousPage()
            }
        }
    }

    override fun showMoreShoppingCartProducts(products: List<ProductUiModel>) {
        shoppingCartRecyclerAdapter.moveToNextPage(products = products)
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
