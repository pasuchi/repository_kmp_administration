package com.amari.amaridesktop.desktop.di

import com.amari.amaridesktop.desktop.data.remote.EquipmentApi
import com.amari.amaridesktop.desktop.data.repository.FirebaseRepositoryImpl
import com.amari.amaridesktop.desktop.domain.repository.FirebaseRepository
import com.amari.amaridesktop.desktop.domain.usecase.CalculateMoneyMaintenanceEquipByPeriodTimeUsecase
import com.amari.amaridesktop.desktop.domain.usecase.FilterOneObjectMaintenanceUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetDateLeftMaintenanceUsecase
import com.amari.amaridesktop.desktop.domain.usecase.GetLastNextMaintenanceDayUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetMaintenanceEquipmentUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetTotalMonthLeftUsecase
import com.amari.amaridesktop.desktop.presentation.EquipmentViewModel
import com.amari.amaridesktop.desktop.util.DateUtil
import com.amari.amaridesktop.desktop.util.DateUtilImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val viewModelAdminMainPresentationModule = module {
    viewModelOf(::EquipmentViewModel)
}

val presentationAdminMainModule = module {
    factoryOf(::GetMaintenanceEquipmentUseCase)
    factoryOf(::FilterOneObjectMaintenanceUseCase)
    factoryOf(::GetLastNextMaintenanceDayUseCase)
    factoryOf(::GetDateLeftMaintenanceUsecase)
    factoryOf(::GetTotalMonthLeftUsecase)
    factoryOf(::CalculateMoneyMaintenanceEquipByPeriodTimeUsecase)
}
val repositoryCoreModule = module {
    singleOf(::FirebaseRepositoryImpl) bind FirebaseRepository::class
    single { EquipmentApi(get()) }
    singleOf(::DateUtilImpl) bind DateUtil::class
}





val DomainAdminMainKoinModule = module {
    includes(presentationAdminMainModule + viewModelAdminMainPresentationModule + repositoryCoreModule)
}
