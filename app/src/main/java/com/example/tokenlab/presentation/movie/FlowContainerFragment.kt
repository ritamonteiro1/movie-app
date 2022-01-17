package com.example.tokenlab.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tokenlab.R
import com.example.tokenlab.di.DaggerFlowContainerComponent
import com.example.tokenlab.di.FlowContainerComponent
import com.example.tokenlab.di.FlowContainerModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

sealed class FlowContainerFragment : Fragment() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var cicerone: Cicerone<Router>

    val component: FlowContainerComponent? by lazy {
        context?.let {
            DaggerFlowContainerComponent
                .builder()
                .flowContainerModule((activity)?.let { fragmentActivity ->
                    FlowContainerModule(
                        fragmentActivity,
                        childFragmentManager
                    )
                })
                .build()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        component?.inject(this)

        return inflater.inflate(R.layout.fragment_flow_container, container, false)
    }

    override fun onPause() {
        super.onPause()
        this.navigatorHolder.removeNavigator()
    }

    override fun onResume() {
        super.onResume()
        this.navigatorHolder.setNavigator(this.navigator)
    }
}

class MovieListFragmentContainer : FlowContainerFragment() {
    companion object {
        fun newInstance() = MovieListFragmentContainer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            cicerone.router.replaceScreen(MovieListScreen())
        }
    }
}

class MovieListScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return MovieListFragment.newInstance()
    }
}

class FavoriteMovieListFragmentContainer : FlowContainerFragment() {
    companion object {
        fun newInstance() = FavoriteMovieListFragmentContainer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            cicerone.router.replaceScreen(FavoriteMovieListScreen())
        }
    }
}

class FavoriteMovieListScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return FavoriteMovieListFragment.newInstance()
    }
}