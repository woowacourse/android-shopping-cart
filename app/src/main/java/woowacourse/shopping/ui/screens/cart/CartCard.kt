package woowacourse.shopping.ui.screens.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.ui.component.AmountController

@Composable
fun CartItemCard(
    item: CartUiModel,
    onClickMinus: () -> Unit,
    onClickPlus: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Color(0xFFAAAAAA), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
            )

            Icon(
                painter = painterResource(R.drawable.ic_dismiss),
                contentDescription = "${item.name} 장바구니에서 제거",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onDelete)
                    .padding(12.dp),
                tint = Color(0xFFAAAAAA),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = "$item.name 상품 이미지",
                modifier = Modifier
                    .width(136.dp)
                    .height(72.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End,
            ) {
                AmountController(
                    amount = item.cartAmount,
                    onClickMinus = { onClickMinus() },
                    onClickAdd = { onClickPlus() },
                    modifier = Modifier.width(126.dp),
                )

                Text(
                    text = item.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartItemCardPreview() {
    CartItemCard(
        item = CartUiModel(
            id = "1",
            imageUrl = "",
            name = "우주선",
            price = "10,000,000원",
            cartAmount = "1",
        ),
        onDelete = { },
        modifier = Modifier.padding(5.dp),
        onClickMinus = { },
        onClickPlus = { },
    )
}
