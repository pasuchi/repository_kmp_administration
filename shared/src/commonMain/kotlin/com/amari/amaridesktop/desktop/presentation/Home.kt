package com.amari.amaridesktop.desktop.presentation

/*import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar*/
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import com.amari.amaridesktop.Colors
import com.amari.amaridesktop.desktop.component.TitleDescription
import com.amari.amaridesktop.desktop.component.ToolBarCustom
import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment
import com.amari.amaridesktop.desktop.presentation.viewmodel.EquipmentViewModel
import com.amari.amaridesktop.desktop.util.FontUtil.getFontUbuntuBold
import com.amari.amaridesktop.desktop.util.dummyEquipmentList
import com.peru.admin_main_presentation.equipment.EquipmentListEvent
import org.koin.compose.viewmodel.koinViewModel

@Preview(widthDp = 1440, heightDp = 900)
@Composable
fun PreviewHome() {
    EquipmentMainScreen(listEquipment = dummyEquipmentList, modifier = Modifier, {})

}

@Composable
fun EquipmentListScreen(modifier: Modifier) {
    val vm: EquipmentViewModel = koinViewModel()
    EquipmentMainScreen(
        listEquipment = vm.uiState.collectAsState().value.listEquipment,
        modifier = modifier,
        vm::onEvent
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentMainScreen(
    listEquipment: List<EquipmentMantListModel>,
    modifier: Modifier,
    event: (EquipmentListEvent) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        ToolBarCustom("Equipos", noArrow = false)

    }) { innerPadding ->
        val listStateIzquierda = rememberLazyListState()
        val listStateDerecha = rememberLazyListState()
        val visibility = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp)
                .background(Color.White)
        ) {
            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Box {
                    LazyColumn(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        state = listStateIzquierda,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(listEquipment) {
                            val isMantenancePreventivo =
                                it.oneObjecEquipment?.typeMaintenance == TypeMaintenanceEquipment.PREVENTIVO.nameEquipment
                            CardEquipment(it, event, isMantenancePreventivo, seeMore = {
                                visibility.value = visibility.value.not()
                            })
                        }
                    }
                    VerticalScrollbar(
                        // listStateDerecha en la otra
                        rememberScrollbarAdapter(listStateIzquierda), Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .padding(end = 2.dp), style = ScrollbarStyle(
                            minimalHeight = 48.dp,
                            thickness = 12.dp,
                            shape = RoundedCornerShape(6.dp),
                            hoverDurationMillis = 300,
                            unhoverColor = Colors.skyBlue.copy(alpha = 0.6f),
                            hoverColor = Colors.skyBlue
                        )
                    )

                    /*  VerticalScrollbar(
                          adapter = rememberScrollbarAdapter(listState),
                          modifier = Modifier
                              .align(Alignment.CenterEnd)
                              .fillMaxHeight()
                              .padding(end = 2.dp),
                          style = ScrollbarStyle(
                              minimalHeight = 48.dp,
                              thickness = 12.dp,                          // grosor visible (default 8.dp)
                              shape = RoundedCornerShape(6.dp),
                              hoverDurationMillis = 300,
                              unhoverColor = Colors.skyBlue.copy(alpha = 0.6f), // visible SIEMPRE, sin hover
                              hoverColor = Colors.skyBlue              // se intensifica al pasar el mouse
                          )
                      )*/
                }
                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        state = listStateDerecha,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(listEquipment) {
                            SeeMoreEquipment(equipmentMantListModel = it)

                        }

                    }

                    VerticalScrollbar(
                        // listStateDerecha en la otra
                        rememberScrollbarAdapter(listStateDerecha), Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .padding(end = 2.dp), style = ScrollbarStyle(
                            minimalHeight = 48.dp,
                            thickness = 12.dp,
                            shape = RoundedCornerShape(6.dp),
                            hoverDurationMillis = 300,
                            unhoverColor = Colors.skyBlue.copy(alpha = 0.6f),
                            hoverColor = Colors.skyBlue
                        )
                    )

                }

            }
        }
    }

}

@Composable
fun CardEquipment(
    equipmentMantListModel: EquipmentMantListModel,
    event: (EquipmentListEvent) -> Unit,
    isMantenancePreventivo: Boolean = false,
    seeMore: () -> Unit
) {

    OutlinedCard(modifier = Modifier.wrapContentWidth().background(color = Color.White)) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max)
                .background(color = Color.White)
        ) {
            EquipmentPreventivoHeader(equipmentMantListModel = equipmentMantListModel, seeMore = {
                seeMore.invoke()
            })

            /*
            AnimatedVisibility(visibility.value) {
                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .clickable {
                            event(EquipmentListEvent.GetDetailMant(equipmentMantListModel))
                        }.background(color = Color.White)
                ) {
                    val headers = NetworkHeaders.Builder()
                        .set("User-Agent", "Mozilla/5.0")
                        .build()

                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .border(border = BorderStroke(1.dp, Color.Black)),
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data("https://firebasestorage.googleapis.com/v0/b/administracion-amari.appspot.com/o/2024%2FACAZ%2FAugust_1723307995150?alt=media&token=ffaa1731-f1d8-41a4-9cb2-73402da2cb7b")
                            .httpHeaders(headers)
                            .build(),
                        contentDescription = null
                    )
                    AnimatedVisibility(isMantenancePreventivo) {
                        EquipmentPreventivoBodyFirst(equipmentMantListModel = equipmentMantListModel)
                    }

                    AnimatedVisibility(isMantenancePreventivo.not()) {
                        EquipmentCorrectivoBodyFirst(equipmentMantListModel = equipmentMantListModel)
                    }

                }
                AnimatedVisibility(isMantenancePreventivo) {

                    EquipmentPreventivoBody(equipmentMantListModel = equipmentMantListModel)

                }
            }

             */
        }
    }
}


@Composable
fun SeeMoreEquipment(equipmentMantListModel: EquipmentMantListModel) {
    OutlinedCard(modifier = Modifier.wrapContentWidth().background(color = Color.White)) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max)
                .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        // event(EquipmentListEvent.GetDetailMant(equipmentMantListModel))
                    }.background(color = Color.White)
            ) {
                val headers = NetworkHeaders.Builder()
                    .set("User-Agent", "Mozilla/5.0")
                    .build()

                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .border(border = BorderStroke(1.dp, Color.Black)),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://firebasestorage.googleapis.com/v0/b/administracion-amari.appspot.com/o/2024%2FACAZ%2FAugust_1723307995150?alt=media&token=ffaa1731-f1d8-41a4-9cb2-73402da2cb7b")
                        .httpHeaders(headers)
                        .build(),
                    contentDescription = null
                )
                AnimatedVisibility(false) {
                    EquipmentPreventivoBodyFirst(equipmentMantListModel = equipmentMantListModel)
                }

                AnimatedVisibility(false.not()) {
                    EquipmentCorrectivoBodyFirst(equipmentMantListModel = equipmentMantListModel)
                }

            }
            AnimatedVisibility(false) {

                EquipmentPreventivoBody(equipmentMantListModel = equipmentMantListModel)

            }
        }
    }

}


@Composable
private fun EquipmentPreventivoHeader(
    equipmentMantListModel: EquipmentMantListModel,
    seeMore: () -> Unit
) {

    Column(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(Colors.skyBlue).padding(start = 12.dp, end = 12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = equipmentMantListModel.equipmentCode,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = getFontUbuntuBold(), fontSize = 14.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp).width(200.dp)
                    .weight(1f),
                text = equipmentMantListModel.oneObjecEquipment?.equipmentName.orEmpty(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = getFontUbuntuBold(), fontSize = 14.sp,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ir al detalle",
                tint = Color.White,
                modifier = Modifier.size(24.dp).clickable {
                    seeMore.invoke()
                }
            )
        }
    }

}

@Composable
private fun EquipmentCorrectivoBodyFirst(equipmentMantListModel: EquipmentMantListModel) {
    Column(modifier = Modifier.padding(start = 12.dp).background(color = Color.White)) {
        TitleDescription(
            title = "Tipo de mantenimiento ",
            description = equipmentMantListModel.oneObjecEquipment?.typeMaintenance.orEmpty()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "Correctivo realizado",
            description = equipmentMantListModel.oneObjecEquipment?.maintenanceDay.orEmpty()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "No registra Mantenimientos Preventivos en el año",
            description = ""
        )
    }
}

@Composable
private fun EquipmentPreventivoBodyFirst(equipmentMantListModel: EquipmentMantListModel) {
    Column(modifier = Modifier.padding(start = 12.dp).background(color = Color.White)) {
        TitleDescription(
            title = "Periodo de manteminiento : ",
            description = "Cada " + equipmentMantListModel.oneObjecEquipment?.equipmentPeriod.orEmpty()
                .plus(" Meses")
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "Ult. Mant : ",
            description = equipmentMantListModel.oneObjecEquipment?.maintenanceDay.orEmpty()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "Dias que faltan para el proximo mantenimiento : ",
            description = equipmentMantListModel.daysLeft.plus(" Dias")
        )
    }
}

@Composable
private fun EquipmentPreventivoBody(equipmentMantListModel: EquipmentMantListModel) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "Proveedor : ",
            description = equipmentMantListModel.oneObjecEquipment?.supplier.orEmpty(),
            Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        TitleDescription(
            title = "Costo Mantenimiento : ",
            description = equipmentMantListModel.oneObjecEquipment?.payAmount.orEmpty()
                .plus(" ").plus(equipmentMantListModel.oneObjecEquipment?.currency),
            Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleDescription(
            title = "Total recaudado : ",
            description = equipmentMantListModel.totalRecolectMoney.orEmpty().plus(" ")
                .plus(equipmentMantListModel.oneObjecEquipment?.currency),
            Modifier.padding(start = 10.dp, end = 10.dp)
        )

    }
}

fun isNegative(number: Int): Boolean = number < 0


