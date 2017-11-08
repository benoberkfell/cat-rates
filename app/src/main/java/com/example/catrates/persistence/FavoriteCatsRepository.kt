package com.example.catrates.persistence

import com.example.catrates.models.Cat
import io.reactivex.Completable
import io.reactivex.Observable

class FavoriteCatsRepository constructor(val catDao: CatDao) {

    fun get() : Observable<List<Cat>> {
        return catDao.all.toObservable()
    }

    fun put(cat: Cat) : Completable {
        return Completable.fromAction {
            cat.savedTime = System.currentTimeMillis()
            catDao.insertAll(cat)
        }
    }

}