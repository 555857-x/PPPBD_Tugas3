package com.example.pppbdtugas3

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.pppbdtugas3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var profileEdited = false

    companion object {
        private const val MENU_TOP = 1
        private const val MENU_ABOUT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowInsetsControllerCompat(window, binding.root).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(binding.root)

        setupInteractions()
    }

    private fun setupInteractions() = with(binding) {

        btnEditProfile.setOnClickListener {
            profileEdited = !profileEdited

            if (profileEdited) {
                txtBio.setText(R.string.student_bio_edited)
                btnEditProfile.setText(R.string.undo_edit)
                showToast(getString(R.string.profile_updated))
            } else {
                txtBio.setText(R.string.student_bio)
                btnEditProfile.setText(R.string.edit_profile)
                showToast(getString(R.string.profile_restored))
            }
        }

        btnShareProfile.setOnClickListener {
            val shareText = getString(
                R.string.share_profile_text,
                getString(R.string.student_name),
                getString(R.string.student_username),
                getString(R.string.student_program)
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.app_name)
                )
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.share_chooser)
                )
            )
        }

        btnMore.setOnClickListener {
            showProfileMenu()
        }

        statPosts.setOnClickListener {
            showToast(getString(R.string.posts_message))
        }

        statFollowers.setOnClickListener {
            showToast(getString(R.string.followers_message))
        }

        statFollowing.setOnClickListener {
            showToast(getString(R.string.following_message))
        }

        val posts = listOf(
            postOne,
            postTwo,
            postThree,
            postFour,
            postFive,
            postSix
        )

        posts.forEachIndexed { index, post ->
            post.setOnClickListener {
                showToast(
                    getString(
                        R.string.open_post,
                        index + 1
                    )
                )
            }
        }

        tabPosts.setOnClickListener {
            showToast(getString(R.string.posts_tab_message))
        }

        tabTagged.setOnClickListener {
            showToast(getString(R.string.tagged_tab_message))
        }

        navHome.setOnClickListener {
            showToast(getString(R.string.demo_home))
        }

        navSearch.setOnClickListener {
            showToast(getString(R.string.demo_search))
        }

        navAdd.setOnClickListener {
            showToast(getString(R.string.demo_add))
        }

        navActivity.setOnClickListener {
            showToast(getString(R.string.demo_activity))
        }

        navProfile.setOnClickListener {
            showToast(getString(R.string.current_profile))
        }
    }

    private fun showProfileMenu() {
        val popupMenu = PopupMenu(
            this,
            binding.btnMore
        )

        popupMenu.menu.add(
            0,
            MENU_TOP,
            0,
            getString(R.string.menu_top)
        )

        popupMenu.menu.add(
            0,
            MENU_ABOUT,
            1,
            getString(R.string.menu_about)
        )

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                MENU_TOP -> {
                    binding.profileScroll.smoothScrollTo(0, 0)
                    showToast(getString(R.string.back_to_top))
                    true
                }

                MENU_ABOUT -> {
                    showToast(getString(R.string.about_message))
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}