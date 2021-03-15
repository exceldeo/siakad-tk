package app.siakad.siakadtkadmin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtkadmin.domain.ModelContainer
import app.siakad.siakadtkadmin.domain.models.FiturModel

class FeatureRepository() {
    private var features = MutableLiveData<ModelContainer<ArrayList<FiturModel>>>()
    private var updateState = MutableLiveData<ModelContainer<String>>()

    private val featureDB = FirebaseRef(MainRepository.FITUR_REF).getRef()

    fun updateData() {
    }

    fun getUpdateState(): LiveData<ModelContainer<String>> {
        return updateState
    }
}