package com.raka.mockitotutorial.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raka.mockitotutorial.R
import com.raka.mockitotutorial.databinding.ActivityLoginBinding
import io.reactivex.disposables.CompositeDisposable

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModel = ViewModelProviders.of(this,LoginViewModelFactory(ApiServiceImpl())).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        setupObservers()
    }
    private fun setupObservers() {
        binding.viewmodel!!.toastMessage.observe(this, Observer {
            it.getContentIfnotHandled()?.let {
                setToastMessage(it)
            }
        })
        binding.viewmodel!!.onLoading.observe(this, Observer {
            if(it){
                binding.pbLogin.visibility = View.VISIBLE
                setFormGone()
            }else{
                binding.pbLogin.visibility = View.GONE
                setFormVisible()
            }
        })
    }

    private fun setToastMessage(it:String){
        var message:String = ""
        when(it!!){
            "1" -> {message = resources.getString(R.string.username_empty)}
            "2" -> {message = resources.getString(R.string.wrong_email)}
            "3" -> {message = resources.getString(R.string.password_empty)}
            "4" -> {message = resources.getString(R.string.login_fail)}
            "5" -> {message = resources.getString(R.string.login_success)}
        }
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    private fun setFormGone(){
        binding.btnLogin.visibility = View.GONE
        binding.etUsername.visibility = View.GONE
        binding.etPassword.visibility = View.GONE
        binding.tvLoginTitle.visibility = View.GONE
    }

    private fun setFormVisible(){
        binding.btnLogin.visibility = View.VISIBLE
        binding.etUsername.visibility = View.VISIBLE
        binding.etPassword.visibility = View.VISIBLE
        binding.tvLoginTitle.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}