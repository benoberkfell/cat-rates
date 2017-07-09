package com.example.catrates.favorites

import com.example.catrates.persistence.CatDao
import com.example.catrates.models.Cat
import com.example.catrates.persistence.FavoriteCatsRepository
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import rx.Emitter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import javax.inject.Inject

class FavoriteCatsPresenter @Inject constructor(val repo: FavoriteCatsRepository) {


    lateinit private var view: FavoriteCatsView

    private val cats: MutableList<Cat> = mutableListOf()

    private val dateFormat: DateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yy HH:mm:ss")

    fun onAttach(view: FavoriteCatsView) {
        this.view = view
    }

    fun load() {
        repo.get().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cats.clear()
                    cats.addAll(it)
                    view.didLoad()
                }
    }

    fun getItemCount() : Int {
        return cats.size
    }

    fun requestBinding(item: CatAdapterItem, position: Int) {
        val cat = cats[position]

        item.setImageUrl(cat.url)
        item.setSavedTime(DateTime(cat.savedTime).toString(dateFormat))
    }

    interface FavoriteCatsView {
        fun didLoad()
    }

    interface CatAdapterItem {
        fun setImageUrl(url: String)
        fun setSavedTime(string: String)
    }

}