package woowacourse.shopping.feature.format

import java.text.Format

abstract class Formatter(
    format: Format,
) {
    abstract fun apply(price: Int): String
}
