package ru.dikoresearch.recyclerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import ru.dikoresearch.recyclerviewexample.databinding.ActivityMainBinding
import ru.dikoresearch.recyclerviewexample.model.User
import ru.dikoresearch.recyclerviewexample.model.UsersListener
import ru.dikoresearch.recyclerviewexample.model.UsersService
import ru.dikoresearch.recyclerviewexample.screens.UserDetailsFragment
import ru.dikoresearch.recyclerviewexample.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    // actions to be launched when activity is active
    private val actions = mutableListOf<() -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        actions.forEach { it() }
        actions.clear()
    }

    override fun showDetails(user: User) {
        runWhenActive {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user.id))
                .commit()
        }
    }

    override fun goBack() {
        runWhenActive {
            onBackPressed()
        }
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    /**
     * Avoiding [IllegalStateException] if navigation action has been called after restoring app from background.
     * For example: press "Delete" button in details screen, minimize app and then restore it.
     */
    private fun runWhenActive(action: () -> Unit) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            // activity is active -> just execute the action
            action()
        } else {
            // activity is not active -> add action to queue
            actions += action
        }
    }
}