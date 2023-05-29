package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartProductDao
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.uimodel.CartProductUIModel

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    override lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var adapter: ShoppingCartAdapter

    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        setToolBar()
        setAdapter()
        setPresenter()
        setViewSettings()
        setPageMoveClick()
        setTotalCheckBoxClick()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setPresenter() {
        presenter = ShoppingCartPresenter(
            view = this,
            cartProductRepository = CartProductRepositoryImpl(CartProductDao(this))
        )
    }

    private fun setAdapter() {
        val shoppingCartClickListener = object : ShoppingCartClickListener {
            override fun onClickRemove(cartProductUIModel: CartProductUIModel) {
                presenter.removeCartProduct(cartProductUIModel)
            }

            override fun onClickCheckBox(cartProductUIModel: CartProductUIModel) {
                presenter.updateCartProductChecked(cartProductUIModel)
            }

            override fun onClickCountButton(
                cartProductUIModel: CartProductUIModel,
                textView: TextView
            ) {
                presenter.updateCartProductCount(cartProductUIModel, textView)
            }
        }
        adapter = ShoppingCartAdapter(emptyList(), shoppingCartClickListener)
    }

    private fun setViewSettings() {
        binding.rvCartList.adapter = adapter
        updatePageCounter(INIT_PAGE_COUNTER_VIEW)
        presenter.updateSelectedTotal()
    }

    private fun setPageMoveClick() {
        binding.tvPageUp.setOnClickListener {
            presenter.loadNextPage(it.isActivated)
        }
        binding.tvPageDown.setOnClickListener {
            presenter.loadPreviousPage(it.isActivated)
        }
    }

    override fun updateProductItemPrice(cartProductUIModel: CartProductUIModel, tvPrice: TextView) {
        tvPrice.text = getText(R.string.price_format).toString().format(
            cartProductUIModel.productUIModel.price * cartProductUIModel.count.value
        )
    }

    override fun updateCartProduct(cartProducts: List<CartProductUIModel>) {
        adapter.update(cartProducts)
    }

    override fun activatePageUpCounter() {
        binding.tvPageUp.isActivated = true
    }

    override fun deactivatePageUpCounter() {
        binding.tvPageUp.isActivated = false
    }

    override fun activatePageDownCounter() {
        binding.tvPageDown.isActivated = true
    }

    override fun deactivatePageDownCounter() {
        binding.tvPageDown.isActivated = false
    }

    override fun updatePageCounter(count: Int) {
        binding.tvPageCounter.text = count.toString()
    }

    override fun updateTotalCheckbox(totalCheckBoxState: Boolean) {
        binding.checkBoxAll.isChecked = totalCheckBoxState
    }

    override fun updateTotalPrice(totalPrice: Int) {
        binding.tvTotalPrice.text = getText(R.string.price_format).toString().format(totalPrice)
    }

    override fun updateTotalCount(totalCount: Int) {
        binding.btnOrder.text = getText(R.string.order_format).toString().format(totalCount)
    }

    private fun setTotalCheckBoxClick() {
        binding.checkBoxAll.setOnClickListener {
            presenter.changeProductsCheckedState(binding.checkBoxAll.isChecked)
            adapter.update(presenter.loadCartProducts())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun intent(context: Context) = Intent(context, ShoppingCartActivity::class.java)

        private const val INIT_PAGE_COUNTER_VIEW = 1
    }
}
