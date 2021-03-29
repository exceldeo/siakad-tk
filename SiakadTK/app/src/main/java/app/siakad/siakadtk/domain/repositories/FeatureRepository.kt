package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.FiturModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

class FeatureRepository() {
    private var features = MutableLiveData<ModelContainer<ArrayList<FiturModel>>>()
    private var updateState = MutableLiveData<ModelContainer<String>>()

    private val featureDB = FirebaseRef(
        FirebaseRef.FITUR_REF
    ).getRef()

    fun getData() {
    }

    fun getUpdateState(): LiveData<ModelContainer<String>> {
        return updateState
    }
}