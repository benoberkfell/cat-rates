package com.example.catrates.persistence

import com.example.catrates.models.Cat
import rx.Completable
import rx.Emitter
import rx.Observable
import rx.functions.Action1
import javax.inject.Inject

class FavoriteCatsRepository constructor(val catDao: CatDao) {

    fun get() : Observable<List<Cat>> {
        return Observable.create(Action1<Emitter<List<Cat>>> { emitter ->
            val cats = catDao.all
            emitter.onNext(cats)
            emitter.onCompleted()
        }, Emitter.BackpressureMode.BUFFER)
    }

    fun put(cat: Cat) : Completable {
        return Completable.fromAction {
            cat.savedTime = System.currentTimeMillis()
            catDao.insertAll(cat)
        }
    }

}