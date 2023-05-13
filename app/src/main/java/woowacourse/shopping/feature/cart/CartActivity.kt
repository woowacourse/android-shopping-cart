package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductsAdapter
import woowacourse.shopping.feature.list.item.CartProductItem
import woowacourse.shopping.feature.list.item.ListItem

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var presenter: CartActivityContract.Presenter
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!

    private val db: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }

    private lateinit var adapter: CartProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CartActivityPresenter(this, db)
        presenter.setUpData()
        presenter.setUpButton()
        setUpView()
    }

    override fun setUpRecyclerView(cartItems: List<CartProductItem>) {
        adapter = CartProductsAdapter(
            onXClick = { listItem -> itemXClickEvent(listItem) },
        )
        binding.cartProductRv.adapter = adapter
        presenter.updateDataEachPage(1)
    }

    override fun setAfterButtonListener(maxPageNumber: Int) {
        binding.pageAfterTv.setOnClickListener {
            var page: Int = binding.pageNumberTv.text.toString().toInt()

            binding.pageAfterTv.setBackgroundColor(getColor(R.color.teal_200))
            binding.pageBeforeTv.setBackgroundColor(getColor(R.color.teal_200))

            if (page <= 1) {
                binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
            }
            if (page >= maxPageNumber) {
                binding.pageAfterTv.setBackgroundColor(getColor(R.color.gray))
                return@setOnClickListener
            }

            binding.pageNumberTv.text = (++page).toString()
            presenter.updateDataEachPage(page)
        }
    }

    override fun setBeforeButtonListener(maxPageNumber: Int) {
        binding.pageBeforeTv.setOnClickListener {
            var page: Int = binding.pageNumberTv.text.toString().toInt()

            binding.pageBeforeTv.setBackgroundColor(getColor(R.color.teal_200))
            binding.pageAfterTv.setBackgroundColor(getColor(R.color.teal_200))

            if (page >= maxPageNumber) {
                binding.pageAfterTv.setBackgroundColor(getColor(R.color.gray))
            }
            if (page <= 1) {
                binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
                return@setOnClickListener
            }

            binding.pageNumberTv.text = (--page).toString()
            presenter.updateDataEachPage(page)
        }
    }

    override fun updateAdapterData(cartItems: List<CartProductItem>) {
        adapter.setItems(cartItems)
    }

    private fun setUpView() {
        binding.pageNumberTv.text = "1"
        binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
    }

    private fun itemXClickEvent(listItem: ListItem) {
        when (listItem) {
            is CartProductItem -> {
                presenter.deleteData(listItem)
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
