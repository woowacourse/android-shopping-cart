package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import java.util.UUID

@Composable
fun CartItem(
    product: Product,
    onDelete : (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .width(324.dp)
            .height(152.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductName(product.name)
                CloseBtn(
                    product = product,
                    onClick = onDelete
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                ProductImage(
                    product.imageUri,
                    modifier = Modifier.size(width = 136.dp, height = 72.dp)
                )
                ProductPrice(product.price)
            }
        }
    }
}

@Composable
private fun ProductName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        fontWeight = FontWeight(700),
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Composable
private fun CloseBtn(
    product: Product,
    onClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_close),
        contentDescription = "삭제 버튼",
        modifier = modifier
            .size(16.dp)
            .clickable(
                onClick = { onClick(product.uuid) }
            )
    )
}

@Composable
private fun ProductPrice(
    price: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = price.toPriceString(),
        fontSize = 16.sp,
        color = Color(0xFF555555),
        modifier = modifier
    )
}

@Preview
@Composable
private fun CartItemPreview() {
    CartItem(
        Product(
        imageUri = "https://media.sodagift.com/img/image/1734582680547.jpg",
        name = "매우매우긴상품명입니다",
        price = 1000000000,
        ),
        {}
    )
}
