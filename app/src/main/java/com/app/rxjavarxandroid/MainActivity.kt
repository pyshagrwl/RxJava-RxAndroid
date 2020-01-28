package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val greetings = "Welcome to RxJava"
    val TAG = "MyActivity"

    lateinit var observer: DisposableObserver<String>
    lateinit var observer2: DisposableObserver<String>
    lateinit var myObservable: Observable<String>

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

        compositeDisposable.add(observer)
        compositeDisposable.add(observer2)

        myObservable.subscribe(observer)
        myObservable.subscribe(observer2)

    }

    override fun onDestroy() {
        super.onDestroy()

//        observer.dispose()
//        observer2.dispose()


// it will dispose compositeDisposable itself we cant add more observers to this after calling below lines
//        compositeDisposable.dispose()

//        it will clear compositeDisposable we can add more observers to this after calling below lines.
        compositeDisposable.clear()
    }

}


