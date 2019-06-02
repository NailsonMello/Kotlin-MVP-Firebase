package aplicacao.contracts

import android.net.Uri
import aplicacao.models.ModelLivro

interface LivroContract{

    interface ViewMain {
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun listaLivros(listlivro: List<ModelLivro>)
        fun usuarioLogado()
        fun usuarioNaoLogado()

    }

    interface ViewCadastro{
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun cadastroSucesso()
        fun cadastroFalhou()
        fun invalidoNome()
        fun invalidoPreco()
        fun invalidoDescricao()
        fun finishActivity()
    }

    interface ViewEditar{
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun editarSucesso()
        fun editarFalhou()
        fun invalidoNome()
        fun invalidoPreco()
        fun invalidoDescricao()
        fun finishActivity()
    }

    interface ViewExcluir{
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun excluirSucesso()
        fun excluirFalhou()
        fun finishActivity()
    }

    interface Presenter{
        fun verificarUser()
        fun verificarAnuncio(idUsuario: String)
        fun sair()
        fun getListaLivros()
        fun saveNotes(androidUid: String, androidName: String, androidVersion: String, androidRelease: String, androidImagem: Uri)
        fun editNotes(androidUid: String, androidName: String, androidVersion: String, androidRelease: String)
        fun validateInput(androidN: ModelLivro) : Boolean
        fun validateExcluir(androidN: String) : Boolean
        fun excluirLivro(androidUid: String)
        fun excluir(An: String, AndroidUid: String)
    }
}