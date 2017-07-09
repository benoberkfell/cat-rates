package com.example.catrates

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.example.catrates.catapi.CatResponse
import com.example.catrates.catlist.CatsFragment
import com.example.catrates.favorites.FavoriteCatsFragment
import com.nytimes.android.external.store.base.impl.BarCode
import com.nytimes.android.external.store.base.impl.Store
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        HasSupportFragmentInjector,
        CatsFragment.CatFragmentInteractionListener {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var catStore: Store<CatResponse, BarCode>

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.bottom_navigation_view)
    lateinit var tabs: BottomNavigationView

    val catFragment: CatsFragment = CatsFragment.newInstance()
    val favesFragment: FavoriteCatsFragment = FavoriteCatsFragment.newInstance()

    lateinit var viewPagerAdapter: CatsPagerAdapter

    var selectedMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        viewPagerAdapter = CatsPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter

        tabs.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_cats -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.action_favorites -> {
                    viewPager.currentItem = 1
                    true
                }
                else -> {
                    false
                }
            }
        }

        selectedMenuItem = tabs.menu.getItem(0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //no-op
            }

            override fun onPageScrollStateChanged(state: Int) {
                //no-op
            }

            override fun onPageSelected(position: Int) {
                selectedMenuItem?.isChecked = false

                selectedMenuItem = tabs.menu.getItem(position)
                selectedMenuItem?.isChecked = true
            }
        })


    }

    override fun onResume() {
        super.onResume()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun savedACat() {
        favesFragment.update()
    }

    inner class CatsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> catFragment
                else -> favesFragment
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

}
