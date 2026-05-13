package woowacourse.shopping.ui.component.item

import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct

@Composable
fun ShoppingItem(
    count: () -> Int,
    product: Product,
    isContainedInCart: () -> Boolean,
    onAddInCart: (PurchaseProduct) -> Unit,
    onAdd: () -> Unit,
    onMinus: () -> Unit,
    onDelete: () -> Unit,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(154.dp)
                .height(206.dp)
                .clickable(
                    onClick = {
                        onClick(product)
                    },
                ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box {
            ProductImage(product.imageUri, Modifier.size(154.dp))
            CirclePlusBtn(
                onClick = { onAddInCart(PurchaseProduct(product = product)) },
                modifier =
                    Modifier
                        .padding(end = 15.dp)
                        .align(Alignment.BottomEnd)
                        .visible(!isContainedInCart()),
            )
            QuantitySelector(
                count = count(),
                onAdd = onAdd,
                onMinus = onMinus,
                onDelete = onDelete,
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .visible(isContainedInCart()),
            )
        }

        ProductInfo(product.name, product.price)
    }
}

@Composable
private fun ProductInfo(
    name: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .padding(horizontal = 9.dp)
                .width(154.dp),
    ) {
        Text(
            text = name,
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier,
        )

        Text(
            text = price.toPriceString(),
            fontSize = 16.sp,
            color = Color(0xFF555555),
            modifier = Modifier,
        )
    }
}

fun Int.toPriceString(): String {
    val formatter = DecimalFormat("###,###")
    return "${formatter.format(this)}원"
}

@Preview(showBackground = true)
@Composable
private fun ShoppingItemPreview1() {
    ShoppingItem(
        { 0 },
        Product(
            imageUri = "https://media.sodagift.com/img/image/1734582680547.jpg",
            name = "매우매우긴상품명입니다",
            price = 1000000000,
        ),
        onClick = {},
        onAdd = {},
        onMinus = {},
        onDelete = {},
        onAddInCart = {},
        isContainedInCart = { false },
    )
}

@Preview(showBackground = true)
@Composable
private fun ShoppingItemPreview2() {
    ShoppingItem(
        { 1 },
        Product(
            imageUri = "https://media.sodagift.com/img/image/1734582680547.jpg",
            name = "매우매우긴상품명입니다",
            price = 1000000000,
        ),
        onClick = {},
        onAdd = {},
        onMinus = {},
        onDelete = {},
        onAddInCart = {},
        isContainedInCart = { true },
    )
}
