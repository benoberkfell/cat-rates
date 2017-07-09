package com.example.catrates.catlist

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.catrates.R
import com.example.catrates.models.Cat
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CatsFragment : Fragment(), CatListPresenter.CatListView {
    companion object {
        fun newInstance(): CatsFragment {
            return CatsFragment()
        }
    }

    @Inject lateinit var presenter: CatListPresenter

    @Inject lateinit var picasso: Picasso

    @BindView(R.id.cats_recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.swipe_layout)
    lateinit var swipeLayout: SwipeRefreshLayout

    private var mListener: CatFragmentInteractionListener? = null

    private val adapter: CatsAdapter = CatsAdapter()

    //region Fragment api methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_cats, container, false)

        ButterKnife.bind(this, view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        presenter.onAttach(this)

        swipeLayout.setOnRefreshListener {
            presenter.refresh()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.load()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
        if (context is CatFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement CatFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    //endregion

    //region Presenter interface methods
    override fun didLoad(itemCount: Int) {
        swipeLayout.isRefreshing = false
        adapter.notifyItemRangeChanged(0, itemCount)
    }

    override fun failedToLoad() {
        swipeLayout.isRefreshing = false
        Snackbar.make(recyclerView, getString(R.string.error_loading_cats), Snackbar.LENGTH_LONG).show()
    }

    override fun savedACat() {
        Snackbar.make(recyclerView, getString(R.string.saved_this_cat), Snackbar.LENGTH_LONG).show()
        mListener?.savedACat()
    }
    //endregion


    //region RecyclerView adapter
    inner class CatsAdapter : RecyclerView.Adapter<CatsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cat_list_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            presenter.requestBinding(holder, position)
        }

        override fun getItemCount(): Int {
            return presenter.getItemCount()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CatListPresenter.CatAdapterItem {
            private fun setImageUrl(url: String) {
                picasso
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imageView)
            }

            override fun setCat(cat: Cat) {
                setImageUrl(cat.url)

                imageView.setOnLongClickListener {
                    presenter.saveCat(cat)
                    true
                }
            }

            val imageView: ImageView = itemView.findViewById(R.id.image_view)
        }
    }
    //endregion

    interface CatFragmentInteractionListener {
        fun savedACat()
    }

}