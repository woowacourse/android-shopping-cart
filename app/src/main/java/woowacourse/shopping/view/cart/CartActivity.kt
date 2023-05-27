package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.common.PriceFormatter
import woowacourse.shopping.data.CartProductSqliteProductRepository
import woowacourse.shopping.data.ProductMockWebRepository
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    private lateinit var adpater: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setActionBar()
        setPresenter()
        presenter.fetchProducts()
        observeTotalPrice()
        observeTotalCount()
        onAllCheckSelected()
        showAllCheckBox()
    }

    private fun setBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
    }

    private fun setActionBar() {
        setSupportActionBar(binding.CartToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setPresenter() {
        presenter = CartPresenter(this, CartProductSqliteProductRepository(CartDBHelper(this)), ProductMockWebRepository())
    }

    override fun showProducts(
        cartProducts: List<CartProductModel>,
        isExistUndo: Boolean,
        isExistNext: Boolean,
        count: String,
    ) {
        adpater = CartAdapter(
            cartProducts,
            object : CartAdapter.OnItemClick {
                override fun onRemoveClick(id: Int) {
                    presenter.removeProduct(id)
                }

                override fun onNextClick() {
                    presenter.fetchNextPage()
                }

                override fun onUndoClick() {
                    presenter.fetchUndoPage()
                }

                override fun onPlusClick(id: Int) {
                    presenter.plusCount(id)
                    presenter.setupTotalPrice()
                }

                override fun onMinusClick(id: Int) {
                    presenter.subCount(id)
                    presenter.setupTotalPrice()
                }

                override fun onItemCheckChanged(id: Int, checked: Boolean) {
                    presenter.updateItemCheck(id, checked)
                    presenter.setupTotalPrice()
                    presenter.setupTotalCount()
                    binding.allCheck.setOnCheckedChangeListener { _, _ -> }
                    showAllCheckBox()
                    onAllCheckSelected()
                }
            },
            isExistUndo,
            isExistNext,
            count,
        )
        binding.recyclerCart.adapter = adpater
    }

    override fun showOtherPage(size: Int) {
        adpater.updateCartItems(size)
    }

    override fun notifyRemoveItem(position: Int) {
        adpater.removeCartItems(position)
    }

    override fun onAllCheckSelected() {
        binding.allCheck.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                presenter.setAllCheck()
            } else {
                presenter.setAllUncheck()
            }
        }
    }

    override fun showAllCheckBox() {
        binding.allCheck.isChecked = presenter.setAllCheckCondition()
    }

    private fun observeTotalPrice() {
        presenter.totalPrice.observe(this) {
            binding.totalPrice.text = getString(R.string.korean_won, PriceFormatter.format(it))
        }
    }

    private fun observeTotalCount() {
        presenter.totalCount.observe(this) {
            binding.order.text = getString(R.string.order_text, it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.handleNextStep(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun handleBackButtonClicked() {
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
