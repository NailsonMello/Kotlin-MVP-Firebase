package aplicacao.views.main

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aplicacao.contracts.LivroContract
import aplicacao.models.ModelLivro
import aplicacao.presenters.LivroPresenter
import barbearia.com.kotlinapplicationtest.R
import kotlinx.android.synthetic.main.activity_editar_livro.*

class EditarLivroActivity : AppCompatActivity(), LivroContract.ViewEditar{

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_livro)


        var strUid: String = intent.getStringExtra("UidLivro")
        var strNome: String = intent.getStringExtra("NomeLivro")
        var strPreco: String = intent.getStringExtra("PrecoLivro")
        var strDescricao: String = intent.getStringExtra("DescricaoLivro")

        edit_name_editar.setText(strNome)
        edit_preco_editar.setText(strPreco)
        edit_desc_editar.setText(strDescricao)


        val presenter = LivroPresenter(this@EditarLivroActivity)
        btn_cadastrar_livro_editar.setOnClickListener {
            progressDialog = ProgressDialog(this@EditarLivroActivity)
            progressDialog!!.setTitle("Editando cadastro")
            progressDialog!!.setMessage("Aguarde, a edição esta sendo concluida")
            progressDialog!!.setCanceledOnTouchOutside(false)

            presenter.editNotes(strUid.toString(), edit_name_editar.text.toString(),
                    edit_preco_editar.text.toString(), edit_desc_editar.text.toString())
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

    override fun editarSucesso() {

        Toast.makeText(this@EditarLivroActivity, "Livro editado com sucesso!!!", Toast.LENGTH_SHORT).show()
    }

    override fun editarFalhou() {

        Toast.makeText(this@EditarLivroActivity, "Falha ao editar livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoNome() {
        Toast.makeText(this@EditarLivroActivity, "Por favor digite o nome do livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoPreco() {
        Toast.makeText(this@EditarLivroActivity, "Por favor digite o preço do livro", Toast.LENGTH_SHORT).show()
    }

    override fun invalidoDescricao() {

        Toast.makeText(this@EditarLivroActivity, "Por favor digite a descrição do livro", Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        //val intent = Intent(this@EditarLivroActivity,MainActivity::class.java)
        //startActivity(intent)
        finish()
    }

}