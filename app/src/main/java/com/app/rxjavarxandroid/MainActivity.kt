package com.app.rxjavarxandroid

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var myObservableJustWithArray: Observable<Student>

    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        myObservableJustWithArray = Observable.fromIterable(getStudents())


        compositeDisposable.add(
            myObservableJustWithArray
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->t.name.toUpperCase() }
                .subscribe{ println(it)}
        )
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


