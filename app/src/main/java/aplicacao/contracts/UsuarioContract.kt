package aplicacao.contracts

import aplicacao.models.ModelLivro
import aplicacao.models.ModelUsuario

interface UsuarioContract {

    interface ViewCadastro{
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun cadastroSucesso()
        fun cadastroFalhou()
        fun invalidoNome()
        fun invalidoEmail()
        fun invalidoSenha()
        fun finishActivity()
    }

    interface ViewLogin{
        fun mostrarCarregamento()
        fun esconderCarregamento()
        fun loginSucesso()
        fun loginFalhou()
        fun invalidoEmail()
        fun invalidoSenha()
        fun finishActivity()
    }

    interface Presenter{
        fun criarConta(nome: String, email: String, senha: String)
        fun valideInputCadastro(mod: ModelUsuario): Boolean
        fun loginUsuario(email: String, senha: String)
        fun valideInputLogin(mod: ModelUsuario): Boolean
    }
}