package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val greetings = "Welcome to RxJava"
    private val TAG = "MyActivity"

    private lateinit var observer: DisposableObserver<String>
    private lateinit var observer2: DisposableObserver<String>
    private lateinit var myObservable: Observable<String>

    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        myObservable = Observable.just(greetings)

        observer = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: String) {
                Log.e(TAG, "onNext method called")

                txtvw.setText(t)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }


        observer2 = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: String) {
                Log.e(TAG, "onNext method called")

                Toast.makeText(applicationContext, "Hello From Observer2", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer2)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}


