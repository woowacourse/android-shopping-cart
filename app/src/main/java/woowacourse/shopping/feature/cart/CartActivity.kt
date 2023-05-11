package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.CartProduct
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.list.adapter.CartProductListAdapter
import woowacourse.shopping.feature.list.item.CartProductListItem
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem

class CartActivity : AppCompatActivity() {
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!
    private val dbHandler: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }
    private val onePageCartProductItemsNumber = 5
    private lateinit var carts: List<CartProductListItem>

    lateinit var adapter: CartProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carts = dbHandler.getCartProducts().map(CartProduct::toItem)

        adapter = CartProductListAdapter(
            getOnePageCartProductItems(carts, 1),
            onXClick = { listItem -> itemXClickEvent(listItem) }
        )
        binding.cartProductRv.adapter = adapter

        // 페이지 번호와 페이지 넘기는 버튼 설정
        binding.pageNumberTv.text = "1"
        binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))

        // 페이지 버튼 클릭 이벤트들
        binding.pageBeforeTv.setOnClickListener {
            var pageNumber: Int = binding.pageNumberTv.text.toString().toInt()

            binding.pageBeforeTv.setBackgroundColor(getColor(R.color.teal_200))
            binding.pageAfterTv.setBackgroundColor(getColor(R.color.teal_200))
            if (pageNumber >= getMaxPageNumber(carts.size))
                binding.pageAfterTv.setBackgroundColor(getColor(R.color.gray))
            if (pageNumber <= 1) {
                binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
                return@setOnClickListener
            }

            binding.pageNumberTv.text = (--pageNumber).toString()
            adapter.setItems(getOnePageCartProductItems(carts, pageNumber))
        }

        binding.pageAfterTv.setOnClickListener {
            var pageNumber: Int = binding.pageNumberTv.text.toString().toInt()

            binding.pageAfterTv.setBackgroundColor(getColor(R.color.teal_200))
            binding.pageBeforeTv.setBackgroundColor(getColor(R.color.teal_200))
            if (pageNumber <= 1)
                binding.pageBeforeTv.setBackgroundColor(getColor(R.color.gray))
            if (pageNumber >= getMaxPageNumber(carts.size)) {
                binding.pageAfterTv.setBackgroundColor(getColor(R.color.gray))
                return@setOnClickListener
            }

            binding.pageNumberTv.text = (++pageNumber).toString()
            adapter.setItems(getOnePageCartProductItems(carts, pageNumber))
        }
    }

    private fun getOnePageCartProductItems(
        carts: List<CartProductListItem>,
        pageNumber: Int
    ): List<CartProductListItem> {
        val onePageItemNumberStart =
            pageNumber * onePageCartProductItemsNumber - onePageCartProductItemsNumber
        val onePageItemNumberEnd = pageNumber * onePageCartProductItemsNumber

        return carts.filterIndexed { index, _ ->
            index in onePageItemNumberStart until onePageItemNumberEnd
        }
    }

    private fun getMaxPageNumber(cartsSize: Int): Int {
        Log.d("debug_page", "cartsSize: $cartsSize")
        Log.d("debug_page", "maxPage: ${(cartsSize - 1) / onePageCartProductItemsNumber + 1}")
        if (cartsSize == 0) return 1
        return (cartsSize - 1) / onePageCartProductItemsNumber + 1
    }

    private fun itemXClickEvent(listItem: ListItem) {
        when (listItem) {
            is CartProductListItem -> {
                dbHandler.deleteColumn(listItem.toDomain())
                carts = dbHandler.getCartProducts().map(CartProduct::toItem)
                adapter.setItems(carts)
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
