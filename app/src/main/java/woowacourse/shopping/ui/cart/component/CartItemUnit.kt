package woowacourse.shopping.ui.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.component.QuantityControlButton
import woowacourse.shopping.ui.common.component.ShoppingImage

@Composable
fun CartItemUnit(
    cartItem: CartItem,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(18.dp),
    ) {
        CartItemHeader(cartItem = cartItem, onClick = onDeleteClick)

        Spacer(Modifier.size(20.dp))

        CartItemBody(
            cartItem = cartItem,
            onAddClick = onAddClick,
            onRemoveClick = onRemoveClick,
        )
    }
}

@Composable
private fun CartItemHeader(
    cartItem: CartItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = cartItem.product.name,
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기",
            modifier = Modifier.clickable(onClick = onClick),
            tint = Color.Gray,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun CartItemBody(
    cartItem: CartItem,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    val price = cartItem.totalPrice.value
    val formatted = String.format("%,d", price)

    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        ShoppingImage(
            model = cartItem.product.imageUrl,
            contentDescription = "상품 이미지",
            modifier =
                Modifier
                    .width(136.dp)
                    .height(72.dp),
        )
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            QuantityControlButton(
                count = cartItem.quantity,
                onIncreaseClick = onAddClick,
                onDecreaseClick = onRemoveClick,
                modifier = Modifier,
            )

            Text(
                text = "$formatted 원",
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier,
            )
        }
    }
}

@Preview(showBackground = true, name = "카트 아이템 유닛")
@Composable
private fun CartItemUnitPreview() {
    val cartItem =
        CartItem(
            Product(
                name = "스피또",
                price = Money(1000),
                imageUrl = "",
            ),
            2,
        )
    CartItemUnit(cartItem = cartItem, onDeleteClick = {}, onAddClick = {}, onRemoveClick = {})
}

@Preview(showBackground = true, name = "이름과 닫기아이콘")
@Composable
private fun CartItemHeaderPreview() {
    val cartItem =
        CartItem(
            Product(
                name = "스피또",
                price = Money(1000),
                imageUrl = "",
            ),
            2,
        )
    CartItemHeader(cartItem = cartItem, onClick = {})
}

@Preview(showBackground = true, name = "사진과 수량, 금액")
@Composable
private fun CartItemBodyPreview() {
    val cartItem =
        CartItem(
            Product(
                name = "스피또",
                price = Money(1000),
                imageUrl = "",
            ),
            2,
        )
    CartItemBody(
        cartItem = cartItem,
        onAddClick = {},
        onRemoveClick = {},
    )
}
