package app.siakad.siakadtkadmin.domain.repositories

import app.siakad.siakadtkadmin.domain.db.ref.FirebaseRef
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelState
import app.siakad.siakadtkadmin.domain.models.PenggunaModel
import app.siakad.siakadtkadmin.domain.utils.helpers.model.UserRoleModel
import app.siakad.siakadtkadmin.domain.utils.listeners.announcement.AnnouncementAddListener
import app.siakad.siakadtkadmin.domain.utils.listeners.login.LoginListener
import app.siakad.siakadtkadmin.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.register.RegisterListener
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.registration.RegistrationListListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserDetailListener
import app.siakad.siakadtkadmin.domain.utils.listeners.user.UserListListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UserRepository {
  private val eventListeners: ArrayList<Any> = arrayListOf()

  private val userDB = FirebaseRef(
    FirebaseRef.USER_REF
  ).getRef()

  fun initGetUserListListener(listener: Any, verified: Boolean = true) {
    val eventListener = userDB.orderByChild("role").equalTo(UserRoleModel.SISWA.str)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            if (listener is UserListListener) {
              listener.updateUserItem(
                ModelContainer(
                  status = ModelState.SUCCESS,
                  data = data
                )
              )
            }
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            if (listener is UserListListener) {
              if (data.status == verified) {
                listener.addUserItem(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = data
                  )
                )
              }
            } else if (listener is AnnouncementAddListener) {
              if (data.status) {
                listener.addUserItem(
                  ModelContainer(
                    status = ModelState.SUCCESS,
                    data = data
                  )
                )
              }
            }
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            if (listener is UserListListener) {
              listener.removeUserItem(
                ModelContainer(
                  status = ModelState.SUCCESS,
                  data = data
                )
              )
            }
          }
        }
      })

    eventListeners.add(eventListener)
  }

  fun initGetUserListByClassListener(listener: UserListListener, kelasId: String) {
    val eventListener = userDB.orderByChild("kelasId").equalTo(kelasId)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            listener.updateUserItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            listener.addUserItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
          val data: PenggunaModel? =
            snapshot.getValue(PenggunaModel::class.java)
          if (data != null) {
            data.userId = snapshot.key.toString()

            listener.removeUserItem(
              ModelContainer(
                status = ModelState.SUCCESS,
                data = data
              )
            )
          }
        }
      })

    eventListeners.add(eventListener)
  }

  fun getUserById(listener: Any, id: String) {
    val eventListener = userDB.orderByKey().equalTo(id).addChildEventListener(object : ChildEventListener {
      override fun onCancelled(error: DatabaseError) {}

      override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

      override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

      override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val user = snapshot.getValue(PenggunaModel::class.java)

        if (user != null) {
          user.userId = snapshot.key.toString()
          if (listener is RegistrationListListener) {
            listener.setUser(ModelContainer.getSuccesModel(user))
          } else if (listener is AnnouncementAddListener) {
            listener.setUserById(ModelContainer.getSuccesModel(user))
          } else if (listener is OrderListListener) {
            listener.setUser(ModelContainer.getSuccesModel(user))
          } else if (listener is RegistrationListListener) {
            listener.setUser(ModelContainer.getSuccesModel(user))
          }
        }
      }

      override fun onChildRemoved(snapshot: DataSnapshot) {}
    })

    eventListeners.add(eventListener)
  }

  fun getUserByEmail(listener: LoginListener, email: String) {
    val eventListener = userDB.orderByChild("email").equalTo(email)
      .addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val user = snapshot.getValue(PenggunaModel::class.java)

          if (user != null) {
            user.userId = snapshot.key.toString()
            listener.setUer(ModelContainer.getSuccesModel(user))
          }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}
      })

    eventListeners.add(eventListener)
  }

  fun insertData(listener: RegisterListener, pengguna: PenggunaModel) {
    val newKey = pengguna.userId
    val newData = PenggunaModel(
      userId = newKey,
      alamat = pengguna.alamat,
      email = pengguna.email,
      nama = pengguna.nama,
      noHP = pengguna.noHP,
      passwd = pengguna.passwd,
      role = UserRoleModel.ADMIN.str
    )

    userDB.child(newKey).setValue(newData).addOnSuccessListener {
      listener.notifyDataInsertStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener {
      listener.notifyDataInsertStatus(ModelContainer.getFailModel())
    }
  }

  fun updateUserData(listener: Any, data: PenggunaModel) {
    val newData = data.toMap()
    val childUpdates = hashMapOf<String, Any>(
      "/${data.userId}" to newData
    )

    userDB.updateChildren(childUpdates).addOnSuccessListener {
      if (listener is UserDetailListener) {
        listener.notifyUserDetailChangeStatus(ModelContainer.getSuccesModel("Success"))
      } else if (listener is RegistrationDetailListener) {
        listener.notifyUserDetailChangeStatus(ModelContainer.getSuccesModel("Success"))
      }
    }.addOnFailureListener {
      if (listener is UserDetailListener) {
        listener.notifyUserDetailChangeStatus(ModelContainer.getFailModel())
      } else if (listener is RegistrationDetailListener) {
        listener.notifyUserDetailChangeStatus(ModelContainer.getFailModel())
      }
    }
  }

  fun removeUserData(listener: UserDetailListener, data: PenggunaModel) {
    userDB.child(data.userId).removeValue().addOnSuccessListener {
      listener.notifyUserDeleteStatus(ModelContainer.getSuccesModel("Success"))
    }.addOnFailureListener { listener.notifyUserDeleteStatus(ModelContainer.getFailModel()) }
  }

  fun removeEventListener() {
    eventListeners.forEach {
      if (it is ChildEventListener) {
        userDB.removeEventListener(it)
      } else if (it is ValueEventListener) {
        userDB.removeEventListener(it)
      }
    }
  }
}