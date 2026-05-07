package woowacourse.shopping.ui.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.ui.theme.Gray50
import woowacourse.shopping.ui.util.formattedPrice

@Composable
fun CartCard(
    onDeleteItem: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    productName: String,
    imageUrl: String,
    price: Int,
    quantity: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 18.dp)
                .border(1.dp, Gray40, RoundedCornerShape(5.dp)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = productName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp,
                color = Color.Black,
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "삭제",
                modifier =
                    Modifier
                        .size(16.dp)
                        .clickable {
                            onDeleteItem()
                        },
                tint = Gray40,
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "상품 이미지",
                placeholder = rememberVectorPainter(Icons.Default.CloudSync),
                error = rememberVectorPainter(Icons.Default.CloudOff),
                fallback = rememberVectorPainter(Icons.Default.CloudOff),
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .width(136.dp),
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.width(126.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "-",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp,
                        color = if(quantity > 1) Color.Black else Gray50,
                        modifier =
                            Modifier
                                .size(42.dp)
                                .clickable(enabled = quantity > 1) {
                                    onDecreaseQuantity()
                                }
                    )
                    Text(
                        text = "$quantity",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.size(42.dp)
                    )
                    Text(
                        text = "+",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp,
                        color = Color.Black,
                        modifier =
                            Modifier
                                .size(42.dp)
                                .clickable {
                                    onIncreaseQuantity()
                                }
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formattedPrice(price),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 26.sp,
                    color = Gray50,
                )
            }

        }
    }
}
