package app.alessandrotedesco.axelspringertest.views

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import app.alessandrotedesco.axelspringertest.R
import app.alessandrotedesco.axelspringertest.adapters.RecyclerAdapter
import app.alessandrotedesco.axelspringertest.adapters.RecyclerItem
import app.alessandrotedesco.axelspringertest.models.NewsResponse
import app.alessandrotedesco.axelspringertest.viewmodels.TeasersViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LoadingView, AppCompatActivity() {
    private var teasersList = ArrayList<RecyclerItem>()
    private lateinit var newsAdapter: RecyclerAdapter
    private lateinit var newsViewModel: TeasersViewModel
    override lateinit var loadingScreen: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // set empty title until the data gets loaded
        title = ""

        initLoadingScreen(this)

        // init news viewModel
        newsViewModel = ViewModelProviders.of(this).get(TeasersViewModel::class.java)

        // get news data
        loadNews()
        // start the loading animation
        setLoading(true)

        // set up the news recyclerView
        setupRecyclerView()
    }

    /**
     * starts the news loading call
     */
    private fun loadNews() {
        newsViewModel.init()
        newsViewModel.getNewsRepository()
            .observe(this, Observer<NewsResponse> {
                @Suppress("ConstantConditionIf") // will change after a successful call
                if (it.name == "")
                    handleError()
                else {
                    title = it.name
                    teasersList.addAll(it.articles)
                    newsAdapter.notifyDataSetChanged()
                }
                // stop the loading animation
                setLoading(false)
            })
    }

    /**
     * handle connection error by showing a snackbar with a retry option
     */
    private fun handleError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.noConnectionErrorMessage),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(getString(R.string.retry)) {
            loadNews()
        }.show()
    }

    /**
     * show/hide the loading progressBar and block/allow user interaction with the UI, depending on the loading variable value
     * @param loading true if it should start the loading process, false if it should stop it
     * @see blockInput
     * @see unblockInput
     */
    override fun setLoading(loading: Boolean) {
        // start loading
        if (loading) {
            // show loadingScreen
            loadingScreen.show()
            // prevent user from using the UI
            blockInput()
        }
        // stop loading
        else {
            // dismiss dialog
            loadingScreen.dismiss()
            // allow user to interact with the UI
            unblockInput()
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = RecyclerAdapter(teasersList)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = newsAdapter
        newsRecyclerView.itemAnimator = DefaultItemAnimator()
        newsRecyclerView.isNestedScrollingEnabled = true
    }
}