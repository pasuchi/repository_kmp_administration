package com.amari.amaridesktop.desktop.domain.repository

import coil3.Uri
import com.amari.amaridesktop.desktop.data.EquipmentMantListResponse
import com.amari.amaridesktop.desktop.data.EquipmentMantRequest
import com.amari.amaridesktop.desktop.data.ResultAdministration
import com.amari.amaridesktop.desktop.domain.model.BookingM
import com.amari.amaridesktop.desktop.domain.model.BookingModel
import com.amari.amaridesktop.desktop.domain.model.DepositModel
import com.amari.amaridesktop.desktop.domain.model.EnablersModel
import com.amari.amaridesktop.desktop.domain.model.EquipmentAmariModel
import com.amari.amaridesktop.desktop.domain.model.EquipmentMainModel
import com.amari.amaridesktop.desktop.domain.model.EquipmentModel
import com.amari.amaridesktop.desktop.domain.model.ErrorBooking
import com.amari.amaridesktop.desktop.domain.model.ErrorLoginModel
import com.amari.amaridesktop.desktop.domain.model.ErrorRegisterDocument
import com.amari.amaridesktop.desktop.domain.model.GeneratePaymentOtherModel
import com.amari.amaridesktop.desktop.domain.model.ItemAdd
import com.amari.amaridesktop.desktop.domain.model.PropietarysModel
import com.amari.amaridesktop.desktop.domain.model.ProprietaryAllYearModel
import com.amari.amaridesktop.desktop.domain.model.ProprietaryGeneralMain
import com.amari.amaridesktop.desktop.domain.model.ProprietaryGenerateModel
import com.amari.amaridesktop.desktop.domain.model.StorageWaterImageModel
import com.amari.amaridesktop.desktop.domain.model.StoreBillsModel
import com.amari.amaridesktop.desktop.domain.model.StoreNotificationModel
import com.amari.amaridesktop.desktop.domain.model.SuccesLoginModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment
import com.amari.amaridesktop.desktop.domain.model.WaterRegisterUpdateModel
import com.amari.amaridesktop.desktop.domain.model.WaterReportMainModel
import com.amari.amaridesktop.desktop.domain.model.WaterReporteModel
import com.api.core.domain.model.DepositMainModel
import com.api.core.domain.model.ParkingLotModel
import com.api.core.domain.model.ParkingMainModel
import com.api.core.domain.model.PropietarieExcelMainModel
import com.amari.amaridesktop.desktop.data.SupplierRequest
import com.api.core.domain.supplier.SuppliersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface FirebaseRepository {


    fun getBillingBuilding(
        year: String,
        month: String
    ): Flow<Result<List<ProprietaryGenerateModel>>>

    fun getBillingBuildingOtherDepositParking(
        year: String,
        month: String
    ): Flow<Result<List<ProprietaryGenerateModel>>>


    fun getValuesForUserAllYears(departmentNumber: String): Flow<Result<List<ProprietaryAllYearModel>>>

    fun getValuesForUserStore(
        year: String,
        departmentNumber: String
    ): Flow<Result<List<ProprietaryGeneralMain>>>


    fun getTrustTimeApi(): Flow<Result<LocalDateTime>>

    fun storeBillingBuilding(
        year: String,
        month: String, department: String, proprietaryGenerateModel: ProprietaryGenerateModel
    ): Flow<Result<Boolean>>

    fun storeBillingBuildingOther(
        year: String,
        month: String, department: String, proprietaryGenerateModel: GeneratePaymentOtherModel
    ): Flow<Result<Boolean>>

    fun storeEquipment(equipmentMainModel: EquipmentMainModel): Flow<Result<Boolean>>
    fun storeAdditionalPayment(
        itemAdd: List<ItemAdd>,
        month: String,
        year: String
    ): Flow<Result<Boolean>>

    fun getAdditionalPayment(): Flow<Result<List<ItemAdd>>>

    fun removeEmail(email: String, department: String): Flow<Result<Boolean>>
    fun removePhone(phone: String, department: String): Flow<Result<Boolean>>

    fun storeEmail(number: String, department: String): Flow<Result<Boolean>>
    fun updateEmail(listEmail: List<String>, department: String): Flow<Result<Boolean>>
    fun updatePhone(listNumber: List<String>, department: String): Flow<Result<Boolean>>
    fun storeNumer(number: String, department: String): Flow<Result<Boolean>>
    fun removeParkin(parkingLotModel: ParkingLotModel): Flow<Result<List<ParkingLotModel>>>
    fun removeDeposit(depositModel: DepositModel): Flow<Result<List<ParkingLotModel>>>
    fun getSinglePropietaries(department: String): Flow<Result<PropietarysModel>>
    fun removeProprietary(propietarieModel: PropietarysModel): Flow<Result<Boolean>>
    fun getParkingLots(): Flow<Result<List<ParkingLotModel>>>
    fun getDeposits(): Flow<Result<List<DepositModel>>>
    fun updateProprietary(propietarieModel: PropietarysModel): Flow<Result<Boolean>>
    fun updateParkingLots(parkingLotModel: ParkingLotModel): Flow<Result<Boolean>>
    fun updateDeposits(depositModel: DepositModel): Flow<Result<Boolean>>

    fun validateMasterUser(password: String): Flow<Result<Boolean>>

    fun insertNewPropietaries(propietarieExcelMainModel: PropietarieExcelMainModel): Flow<Result<Boolean>>

    fun updateEnablers(enablersModel: EnablersModel): Flow<Result<Boolean>>

    fun updatePassword(
        oldpassword: String,
        newPassword: String,
        department: String
    ): Flow<Result<Boolean>>

    fun getStoraBillBuilding(): Flow<Result<StoreBillsModel>>

    fun storeParkingLot(parkingMainModel: ParkingMainModel): Flow<Result<Boolean>>

    fun storeDeposit(depositMainModel: DepositMainModel): Flow<Result<Boolean>>

    fun storeBillsBuilding(storeBillsModel: StoreBillsModel): Flow<Result<Boolean>>

    fun saveSupplier(supplierRequest: SupplierRequest): Flow<Result<Boolean>>

    fun updateImageUrlEquipmentMant(
        uniqueKey: String,
        maintenanceDay: String,
        equipmentId: String,
        year: String,
        listUrlRecibos: List<String>,
        listUrlInformes: List<String>,
        listExtornos: List<String> = emptyList(),
        isRefund: Boolean = false,
        amountRefound: String = "",
        insertWhyRefund: String
    ): Flow<Result<Boolean>>

    fun getListSupplier(): Flow<Result<List<SuppliersResponse>>>


    fun getMaintenance(): Flow<Result<List<EquipmentMantListResponse>>>
    fun getMaintenanceByYear(
        year: Int,
        equipment: String,
        typeMaintenanceEquipment: TypeMaintenanceEquipment
    ): Flow<Result<List<EquipmentMantListResponse>>>

    fun saveMaintenance(equipmentMantRequest: EquipmentMantRequest): Flow<Result<Boolean>>

    suspend fun deleteEntry(
        tokenUnique: String,
        dateReserve: String,
        department: String,
        month: String,
        year: String
    ): Flow<Result<Boolean>>


    fun getEquipment(): Flow<Result<List<EquipmentAmariModel>>>

    suspend fun updateBooking(
        tokenUnique: String,
        dateReserve: String,
        placeReserve: String,
        month: String,
        year: String,
        colorPlace: Int
    ): Flow<Result<Boolean>>

    suspend fun storeNotification(
        map: HashMap<String, String>,
        year: String,
        month: String
    ): Flow<Boolean>

    fun getPropietaries(): Flow<List<PropietarysModel>>

    fun getNotification(year: String): Flow<List<StoreNotificationModel>>

    suspend fun sendNotification(token: String, store: StoreNotificationModel)
    suspend fun storeBooking(bookingM: BookingM): Flow<ResultAdministration<Boolean, ErrorBooking>>

    suspend fun getBookingYear(year: String): Flow<ResultAdministration<List<BookingModel>, ErrorBooking>>

    suspend fun storeFireBaseToken(department: String): Flow<String>
    suspend fun getBookingPostCurrentDay(
        year: String,
        date: LocalDate
    ): Flow<ResultAdministration<List<BookingModel>, ErrorBooking>>

    suspend fun updateWarrantyAndImageValueBooking(
        month: String,
        year: String,
        tokenUnique: String,
        uriOperationImage: Uri,
        department: String,
        incidenceText: String,
        dateReserve: String
    ): Flow<ResultAdministration<Boolean, ErrorLoginModel>>

    suspend fun updateBankDestinyBooking(
        month: String,
        year: String,
        tokenUnique: String,
        bankDestiny: String,
        dateReserve: String,
        bankAccountDestiny: String,
        bankNameOwnerDestiny: String
    ): Flow<ResultAdministration<Boolean, ErrorLoginModel>>


    suspend fun storePropietarys(
        map: HashMap<String, List<EquipmentModel>>,
        year: String,
        month: String
    ): Flow<ResultAdministration<Boolean, ErrorRegisterDocument>>

    suspend fun validateCredencials(
        user: String,
        pd: String
    ): Flow<ResultAdministration<SuccesLoginModel, ErrorLoginModel>>

    suspend fun storeImage(store: StorageWaterImageModel): Flow<Result<Boolean>>


    fun updateStorageImageWater(waterRegisterUpdateModel: WaterRegisterUpdateModel): Flow<Result<Boolean>>

    suspend fun getWaterReportAllWithPreviewsMonth(
        month: String,
        year: String
    ): Flow<Result<WaterReportMainModel>>


    suspend fun getWaterReportByPreviewsMonth(): Flow<Result<List<WaterReporteModel>>>


    suspend fun getWaterByDepartment(
        department: String,
        year: String,
        all: Boolean
    ): Flow<Result<List<WaterReporteModel>>>


    fun enablerOptions(): Flow<Result<Map<String, Boolean>>>

}