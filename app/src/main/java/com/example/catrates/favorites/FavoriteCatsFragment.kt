package com.example.catrates.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.catrates.R
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class FavoriteCatsFragment : Fragment(), FavoriteCatsPresenter.FavoriteCatsView {

    companion object {
        fun newInstance(): FavoriteCatsFragment {
            return FavoriteCatsFragment()
        }
    }

    @Inject
    lateinit var presenter: FavoriteCatsPresenter

    @Inject
    lateinit var picasso: Picasso

    @BindView(R.id.favorites_recycler_view)
    lateinit var recyclerView: RecyclerView

    private val adapter: FavoritesAdapter = FavoritesAdapter()


    //region Fragment Methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        ButterKnife.bind(this, view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        presenter.onAttach(this)

        return view
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.load()
    }
    //endregion


    //region Presenter interface methods
    fun update() {
        presenter.load()
    }

    override fun didLoad() {
        adapter.notifyDataSetChanged()
    }
    //endregon

    //region RecyclerView
    inner class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            presenter.requestBinding(holder, position)
        }

        override fun getItemCount(): Int {
            return presenter.getItemCount()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_list_item, parent, false)

            return ViewHolder(view)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), FavoriteCatsPresenter.CatAdapterItem {
            val imageView : ImageView = itemView.findViewById(R.id.image_view)
            val textView: TextView = itemView.findViewById(R.id.saved_time_text_view)

            override fun setImageUrl(url: String) {
                picasso
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imageView)
            }

            override fun setSavedTime(string: String) {
                textView.text = textView.context.getString(R.string.saved_at, string)
            }

        }
    }
    //endregion
}