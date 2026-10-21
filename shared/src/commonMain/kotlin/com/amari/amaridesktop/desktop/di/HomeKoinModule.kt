package com.amari.amaridesktop.desktop.di

import com.amari.amaridesktop.desktop.data.login.LoginRepositoryImpl
import com.amari.amaridesktop.desktop.data.login.remote.LoginApi
import com.amari.amaridesktop.desktop.data.remote.EquipmentApi
import com.amari.amaridesktop.desktop.data.repository.FirebaseRepositoryImpl
import com.amari.amaridesktop.desktop.data.wrapper.ApiClient
import com.amari.amaridesktop.desktop.domain.login.LoginUsecase
import com.amari.amaridesktop.desktop.domain.login.repository.LoginRepository
import com.amari.amaridesktop.desktop.domain.repository.FirebaseRepository
import com.amari.amaridesktop.desktop.domain.usecase.CalculateMoneyMaintenanceEquipByPeriodTimeUsecase
import com.amari.amaridesktop.desktop.domain.usecase.FilterOneObjectMaintenanceUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetDateLeftMaintenanceUsecase
import com.amari.amaridesktop.desktop.domain.usecase.GetLastNextMaintenanceDayUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetMaintenanceEquipmentUseCase
import com.amari.amaridesktop.desktop.domain.usecase.GetTotalMonthLeftUsecase
import com.amari.amaridesktop.desktop.navigator.LoginRoot
import com.amari.amaridesktop.desktop.navigator.Navigator
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.GestionMantenimientoViewmodel
import com.amari.amaridesktop.desktop.presentation.ui.login.LoginViewmodel
import com.amari.amaridesktop.desktop.presentation.ui.newprovider.NewProviderViewmodel
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.InicioViewmodel
import com.amari.amaridesktop.desktop.presentation.viewmodel.EquipmentViewModel
import com.amari.amaridesktop.desktop.util.DateUtil
import com.amari.amaridesktop.desktop.util.DateUtilImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val viewModelAdminMainPresentationModule = module {
    viewModelOf(::EquipmentViewModel)
    viewModelOf(::NewProviderViewmodel)
    viewModelOf(::LoginViewmodel)
    viewModelOf(::InicioViewmodel)
    viewModelOf(::GestionMantenimientoViewmodel)

}

val presentationAdminMainModule = module {
    factoryOf(::GetMaintenanceEquipmentUseCase)
    factoryOf(::FilterOneObjectMaintenanceUseCase)
    factoryOf(::GetLastNextMaintenanceDayUseCase)
    factoryOf(::GetDateLeftMaintenanceUsecase)
    factoryOf(::GetTotalMonthLeftUsecase)
    factoryOf(::CalculateMoneyMaintenanceEquipByPeriodTimeUsecase)
    factoryOf(::LoginUsecase)

}
val api = module {
    single { ApiClient(get()) }
    single { LoginApi(get()) }
    single { EquipmentApi(get()) }
}
val repositoryCoreModule = module {
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
    singleOf(::FirebaseRepositoryImpl) bind FirebaseRepository::class
    singleOf(::DateUtilImpl) bind DateUtil::class
}

val util = module {
    single { Navigator(LoginRoot) }
}


val DomainAdminMainKoinModule = module {

    includes(presentationAdminMainModule + viewModelAdminMainPresentationModule + repositoryCoreModule + util + api)
}
