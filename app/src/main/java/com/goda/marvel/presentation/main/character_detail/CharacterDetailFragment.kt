package com.goda.marvel.presentation.main.character_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.goda.marvel.R
import com.goda.marvel.common.isPackageInstalled
import com.goda.marvel.common.loadImage
import com.goda.marvel.common.setDivider
import com.goda.marvel.model.CESSResult
import com.goda.marvel.model.Character
import com.goda.marvel.model.Urls
import com.goda.marvel.presentation.common.AppAdapter
import com.goda.marvel.presentation.main.character_detail.callback.*
import kotlinx.android.synthetic.main.charchter_detail_fragment.*
import kotlinx.android.synthetic.main.charchter_detail_fragment.view.*

class CharacterDetailFragment : Fragment(), SeriesDetailsCallback,EventsDetailsCallback,StoriesDetailsCallback,ComicsDetailsCallback, View.OnClickListener {
    private lateinit var viewModel: CharachterDetailViewModel
    private val newsListComics: ArrayList<CESSResult> = ArrayList()
    private val newsListSeries: ArrayList<CESSResult> = ArrayList()
    private val newsListStories: ArrayList<CESSResult> = ArrayList()
    private val newsListEvents: ArrayList<CESSResult> = ArrayList()
    /* val characterDetail by lazy {
         fromBundle(arguments!!).characterDetail

     }*/
    private var characterDetail = Character()
    private val mAdapterComics: AppAdapter<CESSResult> = AppAdapter(this, newsListComics)
    private val mAdapterSeries: AppAdapter<CESSResult> = AppAdapter(this, newsListSeries)
    private val mAdapterStories: AppAdapter<CESSResult> = AppAdapter(this, newsListStories)
    private val mAdapterEvents: AppAdapter<CESSResult> = AppAdapter(this, newsListEvents)
    private fun observeViewModel() {
        viewModel.getComicsList(characterDetail.id)
        viewModel.getEventsList(characterDetail.id)
        viewModel.getSeriesList(characterDetail.id)
        viewModel.getStoriesList(characterDetail.id)
        viewModel.comicResultList.observe(this, comicsObserver)
        viewModel.eventsResultList.observe(this, eventsObserver)
        viewModel.serisResultList.observe(this, seriesObserver)
        viewModel.storiesResultList.observe(this, storiesObserver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharachterDetailViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.charchter_detail_fragment, container, false)
        val args = CharacterDetailFragmentArgs.fromBundle(arguments!!)
        characterDetail = args.characterDetail

        observeViewModel()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShowDetail(view)
        initializeRecyclerView(view)

    }

    private fun ShowDetail(view: View) {

        view.apply {
            detailImage.apply {

                loadImage(this, characterDetail.thumbnail?.path + ".jpg")
            }
            Name.text = characterDetail.name
            val r: String? = characterDetail.description
            r?.let {
                if (TextUtils.isEmpty(it)) {
                    tvDesc.visibility = View.GONE
                    desc.visibility = View.GONE
                } else
                    desc.text = it

            } /*?: run {
                tvDesc.visibility = View.GONE
                desc.visibility = View.GONE
            }*/

        }
        setListner()
    }

    private fun setListner() {

        backImage.setOnClickListener(this)
        wikiLinkLin.setOnClickListener(this)
        detailLinkLin.setOnClickListener(this)
        comicLinkLin.setOnClickListener(this)

    }


    private fun initializeRecyclerView(view: View) {
        /* val linearLayoutManager = LinearLayoutManager(context)
         linearLayoutManager.orientation = RecyclerView.HORIZONTAL*/
        view.apply {
            recyclerViewComics.apply {
                mAdapterComics.setItemViewType(R.layout.item_detail_comic)
                setDivider(R.drawable.recycler_view_divider)
                // layoutManager = linearLayoutManager
                adapter = mAdapterComics
            }
            recyclerViewEvents.apply {
                mAdapterEvents.setItemViewType(R.layout.item_detail_event)
                setDivider(R.drawable.recycler_view_divider)
                //layoutManager = linearLayoutManager
                adapter = mAdapterEvents
            }
            recyclerViewSeries.apply {
                mAdapterSeries.setItemViewType(R.layout.item_detail_series)
                setDivider(R.drawable.recycler_view_divider)

                //layoutManager = linearLayoutManager
                adapter = mAdapterSeries
            }
            recyclerViewStories.apply {
                mAdapterStories.setItemViewType(R.layout.item_detail_story)
                setDivider(R.drawable.recycler_view_divider)

                // layoutManager = linearLayoutManager
                adapter = mAdapterStories
            }
        }

    }

    private val comicsObserver: Observer<List<CESSResult>> = Observer {
        if (it.isEmpty()) {
            tvComics.visibility = View.GONE
            recyclerViewComics.visibility = View.GONE
        } else {
            tvComics.visibility = View.VISIBLE
            recyclerViewComics.visibility = View.VISIBLE
            showResultComics(it)

        }
    }
    private val eventsObserver: Observer<List<CESSResult>> = Observer {
        if (it.isEmpty()) {
            tvEvents.visibility = View.GONE
            recyclerViewEvents.visibility = View.GONE
        } else{
            tvEvents.visibility = View.VISIBLE
            recyclerViewEvents.visibility = View.VISIBLE
            showResultEvents(it)
        }
    }
    private val seriesObserver: Observer<List<CESSResult>> = Observer {
        if (it.isEmpty()) {
            tvseries.visibility = View.GONE
            recyclerViewSeries.visibility = View.GONE
        } else{
            tvseries.visibility = View.VISIBLE
            recyclerViewSeries.visibility = View.VISIBLE
            showResultSeries(it)
        }
    }
    private val storiesObserver: Observer<List<CESSResult>> = Observer {
        if (it.isEmpty()) {
            tvstories.visibility = View.GONE
            recyclerViewStories.visibility = View.GONE
        } else{
            tvstories.visibility = View.VISIBLE
            recyclerViewStories.visibility = View.VISIBLE
            showResultStories(it)
        }
    }

    private fun showResultComics(newsResultsList: List<CESSResult>) {
        newsListComics.clear()
        newsListComics.addAll(newsResultsList)
        mAdapterComics.notifyItemRangeChanged(0, newsResultsList.size - 1)
    }

    private fun showResultStories(newsResultsList: List<CESSResult>) {
        newsListStories.clear()
        newsListStories.addAll(newsResultsList)
        mAdapterStories.notifyItemRangeChanged(0, newsResultsList.size - 1)
    }

    private fun showResultSeries(newsResultsList: List<CESSResult>) {
        newsListSeries.clear()
        newsListSeries.addAll(newsResultsList)
        mAdapterSeries.notifyItemRangeChanged(0, newsResultsList.size - 1)
    }

    private fun showResultEvents(newsResultsList: List<CESSResult>) {
        newsListEvents.clear()
        newsListEvents.addAll(newsResultsList)
        mAdapterEvents.notifyItemRangeChanged(0, newsResultsList.size - 1)
    }

 /*   override fun onClickedItem(item: CESSResult) {
       // findNavController().navigate(CharacterDetailFragmentDirections.actionDetailToListCharacters())
        val bundle=Bundle()
        bundle.putParcelable(
                resources.getString(R.string.CESSResult), item)
        Navigation.findNavController(view!!).navigate(R.id.action_detail_to_cess_list, bundle)
    }*/

    override fun onClick(view: View) {
        when (view.id) {
            R.id.backImage -> findNavController().navigate(CharacterDetailFragmentDirections.actionDetailToListCharacters())
            R.id.wikiLinkLin -> if (characterDetail.urls.size > 1) openChrome(characterDetail.urls[1])

            R.id.comicLinkLin -> if (characterDetail.urls.size > 2) openChrome(characterDetail.urls[2])
            R.id.detailLinkLin -> if (characterDetail.urls.isNotEmpty()) openChrome(characterDetail.urls[0])
        }
    }

    private fun openChrome(urls: Urls) {
        context?.run {
            if (!TextUtils.isEmpty(urls.url)) {
                if (!isPackageInstalled("com.android.chrome")) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(urls.url)
                    startActivity(i)
                } else {
                    val builder = CustomTabsIntent.Builder()
                    builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
                    builder.addDefaultShareMenuItem()
                    builder.setShowTitle(true)
                    builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(this, Uri.parse(urls.url))
                }
            }
        }
    }



    override fun onClickedSeries(item: CESSResult) {
        val bundle=Bundle()
        bundle.putParcelable(
                resources.getString(R.string.CESSResult), item)
        bundle.putParcelableArrayList(
                resources.getString(R.string.CESSResults), newsListSeries)
        findNavController().navigate(R.id.action_detail_to_cessResultListsFragment, bundle)
    }

    override fun onClickedEvent(item: CESSResult) {
        val bundle=Bundle()
        bundle.putParcelable(
                resources.getString(R.string.CESSResult), item)
        bundle.putParcelableArrayList(
                resources.getString(R.string.CESSResults), newsListEvents)
        findNavController().navigate(R.id.action_detail_to_cessResultListsFragment, bundle)    }

    override fun onClickedComic(item: CESSResult) {
        val bundle=Bundle()
        bundle.putParcelable(
                resources.getString(R.string.CESSResult), item)
        bundle.putParcelableArrayList(
                resources.getString(R.string.CESSResults), newsListComics)
        findNavController().navigate(R.id.action_detail_to_cessResultListsFragment, bundle)
    }

    override fun onClickedStories(item: CESSResult) {
        val bundle=Bundle()
        bundle.putParcelable(
                resources.getString(R.string.CESSResult), item)
        bundle.putParcelableArrayList(
                resources.getString(R.string.CESSResults), newsListStories)
        findNavController().navigate(R.id.action_detail_to_cessResultListsFragment, bundle)

    }


}