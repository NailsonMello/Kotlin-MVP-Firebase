package aplicacao.views.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aplicacao.contracts.UsuarioContract
import aplicacao.presenters.UsuarioPresenter
import barbearia.com.kotlinapplicationtest.R
import kotlinx.android.synthetic.main.activity_registrar_usuario.*

class RegistrarUsuarioActivity : AppCompatActivity(), UsuarioContract.ViewCadastro {

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        val presenter = UsuarioPresenter(this@RegistrarUsuarioActivity)
        progressDialog = ProgressDialog(this@RegistrarUsuarioActivity)
        progressDialog!!.setTitle("Cadastrando usuario")
        progressDialog!!.setMessage("Aguarde enquanto cadastramos suas credenciais")
        progressDialog!!.setCanceledOnTouchOutside(false)

        btn_cadastrar_usuario.setOnClickListener {
            presenter.criarConta(edit_nome_usuario.text.toString(),
                    edit_email_usuario.text.toString(),
                    edit_senha_usuario.text.toString())
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

    override fun cadastroSucesso() {

        Toast.makeText(this@RegistrarUsuarioActivity, "Usuario cadastrado com sucesso!!!", Toast.LENGTH_SHORT).show()
    }

    override fun cadastroFalhou() {
        Toast.makeText(this@RegistrarUsuarioActivity, "Falha ao cadastrar usuario!", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoNome() {
        Toast.makeText(this@RegistrarUsuarioActivity, "Você precisa digitar seu nome", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoEmail() {
        Toast.makeText(this@RegistrarUsuarioActivity, "Você precisa digitar seu email", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoSenha() {
        Toast.makeText(this@RegistrarUsuarioActivity, "Você precisa digitar sua senha", Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        val intent = Intent(this@RegistrarUsuarioActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}