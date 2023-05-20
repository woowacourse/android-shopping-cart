package woowacourse.shopping.util.builder

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

fun isolatedViewTypeConcatAdapter(block: ConcatAdapter.() -> Unit): ConcatAdapter =
    ConcatAdapter(isolatedViewTypeConfig).apply(block)

fun <T : ViewHolder> ConcatAdapter.add(adapter: Adapter<T>) {
    addAdapter(adapter)
}

val isolatedViewTypeConfig: ConcatAdapter.Config
    get() = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false)
        .build()
