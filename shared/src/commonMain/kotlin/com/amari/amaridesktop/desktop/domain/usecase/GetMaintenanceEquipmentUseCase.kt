package com.amari.amaridesktop.desktop.domain.usecase

import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.domain.EquipmentMantModel
import com.amari.amaridesktop.desktop.domain.repository.FirebaseRepository
import com.amari.amaridesktop.desktop.util.DateUtil
import com.amari.amaridesktop.desktop.util.pattern2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMaintenanceEquipmentUseCase(
    private val repository: FirebaseRepository,
    private val util: DateUtil
) {

    operator fun invoke(): Flow<Result<List<EquipmentMantListModel>>> =
        repository.getMaintenance().map {
            it.map {
                it.map {
                    EquipmentMantListModel(
                        listEquipmentMantModel = it.listEquipmentMantRequest.map {
                            EquipmentMantModel(
                                equipmentName = it.equipmentName,
                                supplier = it.supplier,
                                payAmount = it.payAmount,
                                currency = it.currency,
                                maintenanceDay = it.maintenanceDay,
                                typeMaintenance = it.typeMaintenance,
                                equipmentPeriod = it.equipmentPeriod,
                                equipmentNumber = it.equipmentNumber,
                                nextMaintenanceDay = it.nextMaintenanceDay,
                                urlListQuotes = it.urlListCotizaciones ?: emptyList(),
                                uniqueKey = it.uniqueKey.orEmpty(),
                                urlListRecibos = it.urlListRecibos ?: emptyList(),
                                urlListInformes = it.urlListInformes ?: emptyList(),
                                urlListExtorno = it.urlListExtornos ?: emptyList(),
                                isRefund = it.isRefund ?: false,
                                amountRefund = it.amountRefund.orEmpty(),
                                insertWhyRefund = it.insertWhyRefund.orEmpty(),
                                monthName = util.getMothValueName(
                                    patternFormat = pattern2,
                                    input = it.maintenanceDay
                                ),
                                reason = it.reason
                            )
                        },
                        equipmentCode = it.equipmentCode,
                        yearStore = it.yearStore
                    )
                }.sortedBy { it.equipmentCode.toInt() }
            }
        }

}