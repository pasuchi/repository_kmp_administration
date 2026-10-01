package com.amari.amaridesktop.desktop
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import com.amari.amaridesktop.AppColors
import com.amari.amaridesktop.desktop.component.ChipMultiple
import com.amari.amaridesktop.desktop.component.TitleDescription
import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment
import com.amari.amaridesktop.desktop.presentation.EquipmentViewModel
import com.amari.amaridesktop.desktop.util.FontUtil.getFontUbuntuBold
import com.peru.admin_main_presentation.equipment.EquipmentListEvent
import org.koin.compose.viewmodel.koinViewModel

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
    Surface(modifier = modifier.fillMaxSize()) {
        var selected by remember { mutableStateOf("Mant Equipos") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
        ) {
            FlowRow(horizontalArrangement = Arrangement.SpaceBetween) {
                ChipMultiple(
                    title = "Mant Equipos",
                    selected = selected,
                    onSelected = {
                        selected = it
                        event(EquipmentListEvent.FilterEquipments())
                    }
                )
                ChipMultiple(
                    title = "Mant caducados",
                    selected = selected,
                    onSelected = {
                        selected = it
                        event(EquipmentListEvent.FilterExpiredEquipments())
                    }
                )
                ChipMultiple(
                    title = "Mant proximos",
                    selected = selected,
                    onSelected = {
                        selected = it
                        event(EquipmentListEvent.filterNextEquipments())

                    }
                )

                Button(
                    onClick = { event.invoke(EquipmentListEvent.GoToCalculator()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.skyBlue
                    )
                ) {
                    Text(
                        text = "Ir Calculadora",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = getFontUbuntuBold(), fontSize = 14.sp
                    )
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                items(listEquipment) {
                    val isMantenancePreventivo =
                        it.oneObjecEquipment?.typeMaintenance == TypeMaintenanceEquipment.PREVENTIVO.nameEquipment
                    CardEquipment(it, event, isMantenancePreventivo)
                }
            }
        }
    }

}

@Composable
fun CardEquipment(
    equipmentMantListModel: EquipmentMantListModel,
    event: (EquipmentListEvent) -> Unit,
    isMantenancePreventivo: Boolean = false
) {

    OutlinedCard(modifier = Modifier.fillMaxWidth().background(color = Color.White)) {
        Column(
            modifier = Modifier.padding(
                bottom = 20.dp
            ).background(color = Color.White)
        ) {
            EquipmentPreventivoHeader(equipmentMantListModel = equipmentMantListModel)

            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    event(EquipmentListEvent.GetDetailMant(equipmentMantListModel))
                }.background(color = Color.White)) {
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
    }
}


@Composable
private fun EquipmentPreventivoHeader(equipmentMantListModel: EquipmentMantListModel) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(AppColors.skyBlue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.1f),
            text = equipmentMantListModel.equipmentCode,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = getFontUbuntuBold(), fontSize = 14.sp
        )

        Text(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp)
                .weight(0.6f),
            text = equipmentMantListModel.oneObjecEquipment?.equipmentName.orEmpty(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = getFontUbuntuBold(), fontSize = 14.sp,
        )
        AnimatedVisibility(
            visible = isNegative(equipmentMantListModel.daysLeft.toInt()),
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    text = "Realizar mantenimiento",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
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

