package com.app.rxjavarxandroid

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var observer: DisposableObserver<Student>
    private val TAG = "MyActivity"

    private lateinit var myObservableJustWithArray: Observable<Student>

    private var compositeDisposable = CompositeDisposable()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        myObservableJustWithArray = Observable.create(object : ObservableOnSubscribe<Student> {
            override fun subscribe(emitter: ObservableEmitter<Student>) {
                val students = getStudents()
                for (student in students) {
                    emitter.onNext(student)
                }
                emitter.onComplete()
            }

        })

        compositeDisposable.add(
            myObservableJustWithArray.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .concatMap { t ->
                    t.name = t.name.toUpperCase()
                    val t1 =
                        Student("Mr. " + t.name, "student1@gmail.com", 20, Date.from(Instant.now()))
                    Observable.just(t, t1, t1)
                }.subscribeWith(getArrayObserver())
        )
    }


    private fun getArrayObserver(): DisposableObserver<Student> {
        observer = object : DisposableObserver<Student>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: Student) {
                Log.e(TAG, "onNext method called: $t")

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }
        return observer
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    @TargetApi(Build.VERSION_CODES.O)
    fun getStudents(): ArrayList<Student> {
        val list = ArrayList<Student>()
        list.add(Student("Student1", "student1@gmail.com", 20, Date.from(Instant.now())))
        list.add(Student("Student2", "student2@gmail.com", 22, Date.from(Instant.now())))
        list.add(Student("Student3", "student3@gmail.com", 19, Date.from(Instant.now())))
        list.add(Student("Student4", "student4@gmail.com", 22, Date.from(Instant.now())))
        list.add(Student("Student5", "student5@gmail.com", 21, Date.from(Instant.now())))
        list.add(Student("Student6", "student6@gmail.com", 20, Date.from(Instant.now())))

        return list
    }

}


