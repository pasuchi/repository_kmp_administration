package com.amari.amaridesktop.desktop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment
import com.amari.amaridesktop.desktop.domain.usecase.CalculateMoneyMaintenanceEquipByPeriodTimeUsecase
import com.amari.amaridesktop.desktop.domain.usecase.FilterOneObjectMaintenanceUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetDateLeftMaintenanceUsecase
import com.amari.amaridesktop.desktop.domain.usecase.GetLastNextMaintenanceDayUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetMaintenanceEquipmentUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetTotalMonthLeftUsecase
import com.peru.admin_main_presentation.equipment.EquipmentListEvent
import com.peru.admin_main_presentation.equipment.EquipmentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


class EquipmentViewModel(
    private val getMaintenanceEquipmentUseCase: GetMaintenanceEquipmentUseCase,
    private val filterOneObjectMaintenanceUseCase: FilterOneObjectMaintenanceUseCase,
    private val getLastNextMaintenanceDayUseCase: GetLastNextMaintenanceDayUseCase,
    private val getDateLeftMaintenanceUsecase: GetDateLeftMaintenanceUsecase,
    private val getTotalMonthLeftUsecase: GetTotalMonthLeftUsecase,
    private val calculateMoneyMaintenanceEquipByPeriodTimeUsecase: CalculateMoneyMaintenanceEquipByPeriodTimeUsecase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(EquipmentUiState())
    val uiState = _uiState.asStateFlow()
    private var tempList: List<EquipmentMantListModel> = emptyList()

    init {
        getEquipment()
    }


    fun onEvent(event: EquipmentListEvent) {
        when (event) {
            is EquipmentListEvent.FilterEquipments -> {
                _uiState.update {
                    it.copy(listEquipment = tempList)
                }
            }

            is EquipmentListEvent.FilterExpiredEquipments -> {
                _uiState.update {
                    it.copy(listEquipment = tempList.filter { it.daysLeft.toInt() < 0 })
                }
            }

            is EquipmentListEvent.filterNextEquipments -> {
                _uiState.update {
                    it.copy(listEquipment = tempList.filter { it.daysLeft.toInt() in 0..31 })
                }
            }

            is EquipmentListEvent.GoToCalculator -> {
                /*
                navigator.navigate(
                    Screen.CalculatorScreen.route, argsList = listOf(
                        ArgsModel(
                            key = "sumEquipment",
                            value = tempList.sumOf { it.totalRecolectMoney?.toDouble() ?: 0.0 }
                                .toString()
                        )
                    )
                )

                 */
            }

            is EquipmentListEvent.GetDetailMant -> {

                /*
                navigator.navigate(
                    Screen.EquipmentDetailMant.route, argsList = listOf(
                        ArgsModel(
                            key = "equipmentMantListModel",
                            value = event.equipmentMantListModel
                        )
                    )
                )

                 */
            }
        }
    }


    fun getEquipment() {
        getMaintenanceEquipmentUseCase.invoke().onEach {
            it.onSuccess { success ->

                addinCriticalValues(success)

                tempList = success
                _uiState.update {
                    it.copy(listEquipment = success)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addinCriticalValues(list: List<EquipmentMantListModel>) {
        list.map {
            val lastNextMaintenanceDay = getLastNextMaintenanceDayUseCase.invoke(listMaintenanceEquipmen = it.listEquipmentMantModel, typeMaintenanceEquipment = TypeMaintenanceEquipment.PREVENTIVO)
            it.oneObjecEquipment = filterOneObjectMaintenanceUseCase.invoke(listEquipmentMaintenance = it.listEquipmentMantModel, typeMaintenanceEquipment = TypeMaintenanceEquipment.PREVENTIVO)
            it.daysLeft = getDateLeftMaintenanceUsecase.invoke(lastNextMaintenanceDay)
            it.totalMonthLeft = getTotalMonthLeftUsecase.invoke(lastNextMaintenanceDay!!)
            it.totalRecolectMoney = calculateMoneyMaintenanceEquipByPeriodTimeUsecase.invoke(it)
        }
    }


}