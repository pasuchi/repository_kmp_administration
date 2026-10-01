package com.amari.amaridesktop.desktop.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.util.FontUtil.getFontUbuntuBold
import com.amari.amaridesktop.desktop.util.FontUtil.getFontUbuntuRegular


@Composable
fun TitleDescription(title:String,description:String,modifier: Modifier = Modifier) {

    Column {
        Text(
            modifier = modifier,
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = getFontUbuntuBold(), fontSize = 14.sp,
        )
        Text(
            modifier = modifier.padding(top = 4.dp),
            text = description,
            color = Color.Black,
            fontFamily = getFontUbuntuRegular(), fontSize = 14.sp,
        )
    }
}