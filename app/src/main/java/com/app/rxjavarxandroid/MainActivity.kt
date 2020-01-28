package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val greetings = "Welcome to RxJava"
    private val TAG = "MyActivity"

    private lateinit var myObservable: Observable<String>
    private lateinit var myObservableJustWithArray: Observable<Array<String>>

    private val list= arrayOf("Item1","Item2,","Item3","Item4")


    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        myObservable = Observable.just(greetings,"Piyush","Agarwal")
//        myObservable = Observable.just(greetings)

        myObservableJustWithArray = Observable.just(list)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )

        compositeDisposable.add(
            myObservableJustWithArray
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getArrayObserver())
        )
    }

    private fun getObserver(): DisposableObserver<String> {
        return  object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: String) {
                Log.e(TAG, "onNext method called: $t")

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }
    }

    private fun getArrayObserver(): DisposableObserver<Array<String>> {
        return  object : DisposableObserver<Array<String>>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: Array<String>) {
                Log.e(TAG, "onNext method called: $t")

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}


