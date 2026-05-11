package woowacourse.shopping.ui.component.item

import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import java.util.UUID

@Composable
fun ShoppingItem(
    product: Product,
    quantity: Int,
    onIncrease: (UUID) -> Unit,
    onDecrease: (UUID) -> Unit,
    onClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(154.dp)
                .height(206.dp)
                .clickable(
                    onClick = {
                        onClick(product.productId)
                    },
                ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier.size(154.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ProductImage(product.imageUri, Modifier.size(154.dp))
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                if (quantity == 0) {
                    AddCircleButton(
                        onAdd = { onIncrease(product.productId) },
                    )
                } else {
                    AmountModifyButton(
                        onIncrease = { onIncrease(product.productId) },
                        onDecrease = { onDecrease(product.productId) },
                        amount = quantity
                    )
                }
            }
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

@Preview
@Composable
private fun ShoppingItemPreview() {
    ShoppingItem(
        Product(
            imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
            name = "매우매우긴상품명입니다",
            price = 1000000000,
        ),
        onClick = {},
        quantity = 1,
        onIncrease = { },
        onDecrease = { },
    )
}
