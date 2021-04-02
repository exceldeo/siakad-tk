package app.siakad.siakadtk.domain.repositories

import androidx.lifecycle.MutableLiveData
import app.siakad.siakadtk.domain.db.ref.FirebaseRef
import app.siakad.siakadtk.domain.models.AktivitasModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

class ActivitiesRepository() {
    private var activitiesList = MutableLiveData<ModelContainer<ArrayList<AktivitasModel>>>()
    private var insertState = MutableLiveData<ModelContainer<String>>()

    private val activitiesDB = FirebaseRef(FirebaseRef.AKTIVITAS_REF).getRef()
}