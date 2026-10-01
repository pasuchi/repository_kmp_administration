package com.amari.amaridesktop.desktop.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.amari.amaridesktop.resources.Res
import com.amari.amaridesktop.resources.ubuntu_bold
import com.amari.amaridesktop.resources.ubuntu_regular
import org.jetbrains.compose.resources.Font

object FontUtil {

    @Composable
    fun getFontUbuntuBold() = FontFamily(Font(Res.font.ubuntu_bold))

    @Composable
    fun getFontUbuntuRegular() = FontFamily(Font(Res.font.ubuntu_regular))
}