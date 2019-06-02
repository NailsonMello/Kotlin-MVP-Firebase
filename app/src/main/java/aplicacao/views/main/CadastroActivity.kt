package aplicacao.views.main

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aplicacao.contracts.LivroContract
import aplicacao.extensions.gone
import aplicacao.models.ModelLivro
import aplicacao.presenters.LivroPresenter
import barbearia.com.kotlinapplicationtest.R
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity(), LivroContract.ViewCadastro {
    var seletImageUri: Uri? = null

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val presenter = LivroPresenter(this@CadastroActivity)
        progressDialog = ProgressDialog(this@CadastroActivity)
        progressDialog!!.setTitle("Cadastrando livro")
        progressDialog!!.setMessage("Aguarde enquanto cadastramos sua publicação")
        progressDialog!!.setCanceledOnTouchOutside(false)

        btn_cadastrar_livro.setOnClickListener {
            presenter.saveNotes("",edit_name.text.toString(), edit_preco.text.toString(), edit_desc.text.toString(), seletImageUri!!)
        }

        imageView_cadastro.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){

            seletImageUri = data.data
            val btimap = MediaStore.Images.Media.getBitmap(contentResolver, seletImageUri)

            val bmap = BitmapDrawable(btimap)
            imageView_cadastro.setBackgroundDrawable(bmap)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun finishActivity() {
        finish()
    }

    override fun mostrarCarregamento() {
        progressBar.gone()
        progressDialog!!.show()

    }

    override fun esconderCarregamento() {
        progressBar.gone()
        progressDialog!!.dismiss()

    }

    override fun cadastroSucesso() {

        Toast.makeText(this@CadastroActivity, "Livro cadastrado com sucesso!!!", Toast.LENGTH_SHORT).show()
    }

    override fun cadastroFalhou() {

        Toast.makeText(this@CadastroActivity, "Falha ao cadastrar livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoNome() {
        Toast.makeText(this@CadastroActivity, "Por favor digite o nome do livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoPreco() {
        Toast.makeText(this@CadastroActivity, "Por favor digite o preço do livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoDescricao() {

        Toast.makeText(this@CadastroActivity, "Por favor digite a descrição do livro", Toast.LENGTH_SHORT).show()
    }


}