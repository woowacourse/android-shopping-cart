package woowacourse.shopping.shoppingcart

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.common.ProductCountClickListener
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.CART_PRODUCT_TO_READ
import woowacourse.shopping.util.generateShoppingCartPresenter
import java.text.DecimalFormat

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

    override fun onDestroy() {
        super.onDestroy()
        presenter.checkAllShoppingCartProducts()
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
        products: List<CartProductUiModel>,
        totalSize: Int,
    ) {
        shoppingCartRecyclerAdapter = ShoppingCartRecyclerAdapter(
            products = products,
            cartClickListener = object : CartClickListener {
                override fun onClickRemoveBtn(id: Int) {
                    presenter.removeShoppingCartProduct(id)
                }

                override fun onClickCheckBox(id: Int, isSelected: Boolean) {
                    presenter.changeShoppingCartProductSelection(id, isSelected)
                }

                override fun onClickCheckAllBtn(products: List<CartProductUiModel>, isSelected: Boolean) {
                    presenter.checkAllBox(products, isSelected)
                }
            },
            showingRule = ShowingShoppingCartProducts(),
            updatePageState = ::setUpPageState,
            totalSize = totalSize,
            countClickListener = object : ProductCountClickListener {
                override fun onPlusClick(id: Int) {
                    presenter.changeShoppingCartProductCount(id, true)
                }

                override fun onMinusClick(id: Int) {
                    presenter.changeShoppingCartProductCount(id, false)
                }
            },
        )

        with(binding) {
            recyclerViewCart.adapter = shoppingCartRecyclerAdapter
            buttonNextPage.setOnClickListener {
                presenter.readMoreShoppingCartProducts()
                checkAllBtnOrNot()
            }
            buttonPreviousPage.setOnClickListener {
                shoppingCartRecyclerAdapter.toPreviousPage()
                checkAllBtnOrNot()
            }
            checkboxTotal.setOnClickListener {
                shoppingCartRecyclerAdapter.checkAllBtn(checkboxTotal.isChecked)
            }
        }
    }

    override fun showMoreShoppingCartProducts(products: List<CartProductUiModel>) {
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

    override fun updateTotalInfo(price: Int, count: Int) {
        binding.textTotalPrice.text =
            getString(R.string.price_format, DecimalFormat("#,###").format(price))
        binding.buttonOrder.text = getString(R.string.order_button_text, count)
    }

    override fun checkAllBtnOrNot() {
        binding.checkboxTotal.isChecked = !shoppingCartRecyclerAdapter.isNotAllSelected
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 0
        private const val NEXT_PAGE = 1
    }
}
