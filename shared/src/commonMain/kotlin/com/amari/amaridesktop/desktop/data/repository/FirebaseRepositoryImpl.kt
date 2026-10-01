package com.amari.amaridesktop.desktop.data.repository

import coil3.Uri
import com.amari.amaridesktop.desktop.data.EquipmentMantListResponse
import com.amari.amaridesktop.desktop.data.EquipmentMantRequest
import com.amari.amaridesktop.desktop.data.ResultAdministration
import com.amari.amaridesktop.desktop.data.SupplierRequest
import com.amari.amaridesktop.desktop.data.remote.EquipmentApi
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
import com.amari.amaridesktop.desktop.domain.repository.FirebaseRepository
import com.api.core.domain.model.DepositMainModel
import com.api.core.domain.model.ParkingLotModel
import com.api.core.domain.model.ParkingMainModel
import com.api.core.domain.model.PropietarieExcelMainModel
import com.api.core.domain.supplier.SuppliersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class FirebaseRepositoryImpl(private val api: EquipmentApi) : FirebaseRepository {
    override fun getBillingBuilding(
        year: String,
        month: String
    ): Flow<Result<List<ProprietaryGenerateModel>>> {
        TODO("Not yet implemented")
    }

    override fun getBillingBuildingOtherDepositParking(
        year: String,
        month: String
    ): Flow<Result<List<ProprietaryGenerateModel>>> {
        TODO("Not yet implemented")
    }

    override fun getValuesForUserAllYears(departmentNumber: String): Flow<Result<List<ProprietaryAllYearModel>>> {
        TODO("Not yet implemented")
    }

    override fun getValuesForUserStore(
        year: String,
        departmentNumber: String
    ): Flow<Result<List<ProprietaryGeneralMain>>> {
        TODO("Not yet implemented")
    }

    override fun getTrustTimeApi(): Flow<Result<LocalDateTime>> {
        TODO("Not yet implemented")
    }

    override fun storeBillingBuilding(
        year: String,
        month: String,
        department: String,
        proprietaryGenerateModel: ProprietaryGenerateModel
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeBillingBuildingOther(
        year: String,
        month: String,
        department: String,
        proprietaryGenerateModel: GeneratePaymentOtherModel
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeEquipment(equipmentMainModel: EquipmentMainModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeAdditionalPayment(
        itemAdd: List<ItemAdd>,
        month: String,
        year: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getAdditionalPayment(): Flow<Result<List<ItemAdd>>> {
        TODO("Not yet implemented")
    }

    override fun removeEmail(
        email: String,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun removePhone(
        phone: String,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeEmail(
        number: String,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateEmail(
        listEmail: List<String>,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updatePhone(
        listNumber: List<String>,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeNumer(
        number: String,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun removeParkin(parkingLotModel: ParkingLotModel): Flow<Result<List<ParkingLotModel>>> {
        TODO("Not yet implemented")
    }

    override fun removeDeposit(depositModel: DepositModel): Flow<Result<List<ParkingLotModel>>> {
        TODO("Not yet implemented")
    }

    override fun getSinglePropietaries(department: String): Flow<Result<PropietarysModel>> {
        TODO("Not yet implemented")
    }

    override fun removeProprietary(propietarieModel: PropietarysModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getParkingLots(): Flow<Result<List<ParkingLotModel>>> {
        TODO("Not yet implemented")
    }

    override fun getDeposits(): Flow<Result<List<DepositModel>>> {
        TODO("Not yet implemented")
    }

    override fun updateProprietary(propietarieModel: PropietarysModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateParkingLots(parkingLotModel: ParkingLotModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateDeposits(depositModel: DepositModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun validateMasterUser(password: String): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun insertNewPropietaries(propietarieExcelMainModel: PropietarieExcelMainModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateEnablers(enablersModel: EnablersModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updatePassword(
        oldpassword: String,
        newPassword: String,
        department: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getStoraBillBuilding(): Flow<Result<StoreBillsModel>> {
        TODO("Not yet implemented")
    }

    override fun storeParkingLot(parkingMainModel: ParkingMainModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeDeposit(depositMainModel: DepositMainModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun storeBillsBuilding(storeBillsModel: StoreBillsModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun saveSupplier(supplierRequest: SupplierRequest): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateImageUrlEquipmentMant(
        uniqueKey: String,
        maintenanceDay: String,
        equipmentId: String,
        year: String,
        listUrlRecibos: List<String>,
        listUrlInformes: List<String>,
        listExtornos: List<String>,
        isRefund: Boolean,
        amountRefound: String,
        insertWhyRefund: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }


    override fun getListSupplier(): Flow<Result<List<SuppliersResponse>>> {
        TODO("Not yet implemented")
    }

    override fun getMaintenance(): Flow<Result<List<EquipmentMantListResponse>>> = flow {
        try {
            val response = api.getMantenimientos()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getMaintenanceByYear(
        year: Int,
        equipment: String,
        typeMaintenanceEquipment: TypeMaintenanceEquipment
    ): Flow<Result<List<EquipmentMantListResponse>>> {
        TODO("Not yet implemented")
    }

    override fun saveMaintenance(equipmentMantRequest: EquipmentMantRequest): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntry(
        tokenUnique: String,
        dateReserve: String,
        department: String,
        month: String,
        year: String
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getEquipment(): Flow<Result<List<EquipmentAmariModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBooking(
        tokenUnique: String,
        dateReserve: String,
        placeReserve: String,
        month: String,
        year: String,
        colorPlace: Int
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun storeNotification(
        map: HashMap<String, String>,
        year: String,
        month: String
    ): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getPropietaries(): Flow<List<PropietarysModel>> {
        TODO("Not yet implemented")
    }

    override fun getNotification(year: String): Flow<List<StoreNotificationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendNotification(
        token: String,
        store: StoreNotificationModel
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun storeBooking(bookingM: BookingM): Flow<ResultAdministration<Boolean, ErrorBooking>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookingYear(year: String): Flow<ResultAdministration<List<BookingModel>, ErrorBooking>> {
        TODO("Not yet implemented")
    }

    override suspend fun storeFireBaseToken(department: String): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookingPostCurrentDay(
        year: String,
        date: LocalDate
    ): Flow<ResultAdministration<List<BookingModel>, ErrorBooking>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateWarrantyAndImageValueBooking(
        month: String,
        year: String,
        tokenUnique: String,
        uriOperationImage: Uri,
        department: String,
        incidenceText: String,
        dateReserve: String
    ): Flow<ResultAdministration<Boolean, ErrorLoginModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBankDestinyBooking(
        month: String,
        year: String,
        tokenUnique: String,
        bankDestiny: String,
        dateReserve: String,
        bankAccountDestiny: String,
        bankNameOwnerDestiny: String
    ): Flow<ResultAdministration<Boolean, ErrorLoginModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun storePropietarys(
        map: HashMap<String, List<EquipmentModel>>,
        year: String,
        month: String
    ): Flow<ResultAdministration<Boolean, ErrorRegisterDocument>> {
        TODO("Not yet implemented")
    }

    override suspend fun validateCredencials(
        user: String,
        pd: String
    ): Flow<ResultAdministration<SuccesLoginModel, ErrorLoginModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun storeImage(store: StorageWaterImageModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateStorageImageWater(waterRegisterUpdateModel: WaterRegisterUpdateModel): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterReportAllWithPreviewsMonth(
        month: String,
        year: String
    ): Flow<Result<WaterReportMainModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterReportByPreviewsMonth(): Flow<Result<List<WaterReporteModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterByDepartment(
        department: String,
        year: String,
        all: Boolean
    ): Flow<Result<List<WaterReporteModel>>> {
        TODO("Not yet implemented")
    }

    override fun enablerOptions(): Flow<Result<Map<String, Boolean>>> {
        TODO("Not yet implemented")
    }
}