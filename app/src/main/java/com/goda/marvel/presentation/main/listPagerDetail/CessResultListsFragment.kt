package com.goda.marvel.presentation.main.listPagerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.goda.marvel.R
import com.goda.marvel.model.CESSResult
import com.goda.marvel.presentation.main.listPagerDetail.CessResultsFragment.Companion.newInstance
import kotlinx.android.synthetic.main.fragment_all_cessresult.*

private const val ARG_Cess = "cessarg"

class CessResultListsFragment : Fragment() {
    val NO_RESERVATIONS = 1
    val DONE = 2
    fun newCessResultsFragment() = CessResultListsFragment()

    val cessResult: CESSResult? by lazy { arguments!!.getParcelable<CESSResult>(resources.getString(R.string.CESSResult)) }
    val cessResultList: ArrayList<CESSResult>? by lazy { arguments!!.getParcelableArrayList<CESSResult>(resources.getString(R.string.CESSResults)) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_all_cessresult, container, false)
        return view
    }

    fun setupViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        var count = 1
        val str = StringBuilder()
        for (item in cessResultList!!.iterator()) {
            //adapter.addFragment(newCessResultsFragment(), "")
            str.clear()
            adapter.addFragment(
                    newInstance(
                            item
                            , str.append(count++).append("/").append(cessResultList!!.size).toString()), ""
            )
        }
        viewpager.adapter = adapter
        viewpager.setClipChildren(false)
        viewpager.setClipToPadding(false)
        val margin = 32
        val padding = 40
        viewpager.setPageMargin(margin)
        viewpager.setPadding(padding * 2, 0, padding * 2, 0)
        viewpager.setPageTransformer(false, ViewPager.PageTransformer() { page, position ->


            when (viewpager.currentItem) {
                0 -> {
                    page.translationX = (-padding).toFloat()
                }
                adapter.count - 1 -> {
                    page.translationX = padding.toFloat()
                }
                else -> {
                    page.translationX = 0f
                }
            }
           /* val MIN_SCALE = 0.65f
            val MIN_ALPHA = 0.3f
            if (position < -1) { // [-Infinity,-1)
// This page is way off-screen to the left.
                page.alpha = .3f
            } else if (position <= 1) { // [-1,1]
                page.scaleX = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.scaleY = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position))
            } else { // (1,+Infinity]
// This page is way off-screen to the right.
                page.alpha = .3f
            }*/
        }/*ZoomOutTransformation()*/)
    }

}