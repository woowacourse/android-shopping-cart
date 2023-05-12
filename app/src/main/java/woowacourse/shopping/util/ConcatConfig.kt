package woowacourse.shopping.util

import androidx.recyclerview.widget.ConcatAdapter.Config

val isolatedViewTypeConfig: Config
    get() = Config.Builder()
        .setIsolateViewTypes(false)
        .build()
