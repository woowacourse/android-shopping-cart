package woowacourse.shopping.shoppingcart

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.CART_PRODUCT_TO_READ
import woowacourse.shopping.util.generateShoppingCartPresenter

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {

    private lateinit var shoppingCartRecyclerAdapter: ShoppingCartRecyclerAdapter
    private val presenter: ShoppingCartContract.Presenter by lazy {
        generateShoppingCartPresenter(this, this)
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
        totalSize: Int,
    ) {
        shoppingCartRecyclerAdapter = ShoppingCartRecyclerAdapter(
            products = products,
            onRemoved = onRemoved,
            showingRule = ShowingShoppingCartProducts(),
            updatePageState = ::setUpPageState,
            totalSize = totalSize,
        )

        with(binding) {
            recyclerViewCart.adapter = shoppingCartRecyclerAdapter
            buttonNextPage.setOnClickListener {
                presenter.readMoreShoppingCartProducts()
            }
            buttonPreviousPage.setOnClickListener {
                shoppingCartRecyclerAdapter.toPreviousPage()
            }
        }
    }

    override fun showMoreShoppingCartProducts(products: List<ProductUiModel>) {
        shoppingCartRecyclerAdapter.toNextPage(products = products)
    }

    private fun setUpPageState(pageNumber: Int, totalSize: Int) {
        binding.textPageNumber.text = "${pageNumber + NEXT_PAGE}"
        binding.buttonPreviousPage.isEnabled = pageNumber != INITIAL_PAGE_NUMBER
        binding.buttonNextPage.isEnabled = hasNextPage(pageNumber, totalSize)
    }

    private fun hasNextPage(pageNumber: Int, totalSize: Int): Boolean {
        return pageNumber < totalSize / CART_PRODUCT_TO_READ &&
            !(
                pageNumber + NEXT_PAGE == totalSize / CART_PRODUCT_TO_READ &&
                    totalSize % CART_PRODUCT_TO_READ == 0
                )
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 0
        private const val NEXT_PAGE = 1
    }
}
