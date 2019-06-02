package aplicacao.views.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aplicacao.contracts.UsuarioContract
import aplicacao.presenters.UsuarioPresenter
import barbearia.com.kotlinapplicationtest.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), UsuarioContract.ViewLogin{

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val presenter = UsuarioPresenter(this@LoginActivity)
        progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog!!.setTitle("Logando")
        progressDialog!!.setMessage("Aguarde enquanto verificamos suas credenciais")
        progressDialog!!.setCanceledOnTouchOutside(false)

        btn_login.setOnClickListener {
            presenter.loginUsuario(edit_email_login.text.toString(), edit_senha_login.text.toString())
        }

        text_criar_conta.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrarUsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun mostrarCarregamento() {
        progressDialog!!.show()
    }

    override fun esconderCarregamento() {
        progressDialog!!.dismiss()
    }

    override fun loginSucesso() {
        Toast.makeText(this@LoginActivity, "Logado com sucesso!!!", Toast.LENGTH_SHORT).show()
    }

    override fun loginFalhou() {
        Toast.makeText(this@LoginActivity, "Falha ao fazer login", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoEmail() {
        Toast.makeText(this@LoginActivity, "Email incorreto", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoSenha() {
        Toast.makeText(this@LoginActivity, "Senha incorreta", Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }

}