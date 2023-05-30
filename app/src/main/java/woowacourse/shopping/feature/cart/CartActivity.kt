package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.cart.source.local.CartLocalDataSourceImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartProductsAdapter
    override lateinit var presenter: CartActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = CartLocalDataSourceImpl(this)
        presenter = CartActivityPresenter(this, db)
        setUpView()
    }

    private fun setUpView() {
        presenter.setUpButton()
        presenter.setBottomView()
        presenter.loadInitialData()
    }

    override fun setUpAdapterData(cartItems: List<CartProductItem>) {
        adapter = CartProductsAdapter(
            onDeleteItem = { item -> presenter.removeItem(item) },
            onCheckItem = { item -> presenter.toggleItemChecked(item) },
            onPlusItem = { item -> presenter.updateItem(item, true) },
            onMinusItem = { item -> presenter.updateItem(item, false) },
        )
        binding.cartProductRv.adapter = adapter
        adapter.setItems(cartItems)
    }

    override fun setButtonClickListener(maxPage: Int) {
        binding.pageAfterTv.setOnClickListener {
            presenter.onNextPage()
        }

        binding.pageBeforeTv.setOnClickListener {
            presenter.onPreviousPage()
        }

        binding.allCheckBox.setOnClickListener {
            val isChecked = binding.allCheckBox.isChecked
            presenter.selectAllItems(isChecked)
        }
    }

    override fun updateButtonsEnabledState(page: Int, maxPage: Int) {
        binding.pageBeforeTv.isEnabled = page > 1
        binding.pageAfterTv.isEnabled = page < maxPage
    }

    override fun updateAdapterData(
        cartItems: List<CartProductItem>,
        selectedStates: List<Boolean>,
    ) {
        adapter.setItems(cartItems, selectedStates)
    }

    override fun setPage(page: Int) {
        binding.pageNumberTv.text = "$page"
    }

    override fun setPrice(totalPrice: Int) {
        binding.totalPriceTv.text = getString(R.string.price_format, totalPrice)
    }

    override fun setOrderNumber(number: Int) {
        binding.orderTv.text = getString(R.string.order_format, number)
    }

    override fun setAllSelected(isChecked: Boolean) {
        binding.allCheckBox.isChecked = isChecked
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
