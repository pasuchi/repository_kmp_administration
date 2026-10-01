package com.amari.amaridesktop.desktop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.AppColors
import com.amari.amaridesktop.desktop.util.FontUtil.getFontUbuntuBold

@Composable
fun ChipMultiple(
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {

    val isSelected = selected == title

    val background = if (isSelected) Color.Blue else AppColors.skyBlue
    val contentColor = if (isSelected) Color.White else Color.White

    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(background, shape = RoundedCornerShape(5.dp))
            .clickable(
                onClick = {
                    onSelected(title)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = title, color = contentColor,
                fontWeight = FontWeight.Bold,
                fontFamily = getFontUbuntuBold(), fontSize = 14.sp
            )

        }
    }

}