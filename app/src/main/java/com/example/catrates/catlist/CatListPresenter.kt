package com.example.catrates.catlist

import com.example.catrates.persistence.CatDao
import com.example.catrates.catapi.CatResponse
import com.example.catrates.models.Cat
import com.example.catrates.persistence.FavoriteCatsRepository
import com.nytimes.android.external.store.base.impl.BarCode
import com.nytimes.android.external.store.base.impl.Store
import rx.Completable
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CatListPresenter @Inject constructor(val catStore: Store<CatResponse, BarCode>,
                                           val favoritesRepo: FavoriteCatsRepository) {

    private val barCode = BarCode("Cats", "AllOfThem")
    private var cats : MutableList<Cat> = mutableListOf()

    lateinit private var view: CatListView

    fun onAttach(view: CatListView) {
        this.view = view
    }

    fun refresh() {
        subscribeToCats(catStore.fetch(barCode))
    }

    fun load() {
        subscribeToCats(catStore.get(barCode))
    }

    private fun subscribeToCats(observable: Observable<CatResponse>) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    cats.clear()
                    cats.addAll(it.cats)
                    view.didLoad(cats.size)
                }, {
                    Timber.e(it, "Oh no! Couldn't get any cats")
                    view.failedToLoad()
                })
    }

    fun getItemCount() : Int {
        return cats.size
    }

    fun requestBinding(item: CatAdapterItem, position: Int) {
        item.setCat(cats[position])
    }

    fun saveCat(cat: Cat) {
        favoritesRepo.put(cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.savedACat()
                }
    }

    interface CatListView {
        fun didLoad(itemCount: Int)
        fun failedToLoad()
        fun savedACat()
    }

    interface CatAdapterItem {
        fun setCat(cat: Cat)
    }

}