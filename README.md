Controle de Cliente e Cidade
===

Projeto responsável por gerir as informações de cadastro, consulta e alteração de clientes e cidades

---

### Stack

| Tecnologia                | Versão         |
| ---                       | ---            |
| Java                      | 11.0.10        |
| SpringBoot                | 2.4.5          |

---
### Como rodar

Mvn clean install

---
### Endpoints
### Cidade:
    
    cadastrar
    http://{ambiente desejado}/cidades     
    parâmetros enviado via Body(Json)
    Ex:
        {
            "nome":"São Paulo",
            "estado":"SP"
        }
---
    consultar todos
    Método GET
    http://{ambiente desejado}/cidades
---
    consultar por nome da cidade
    Método GET
    http://{ambiente desejado}/cidades?nome={nome}  
---
    consultar por estado
    Método GET
    http://{ambiente desejado}/cidades?estado={Unidade federativa}
---
    consultar por nome de cidade e estado
    Método GET
    http://{ambiente desejado}/cidades?nome={nome}&estado={Unidade federativa}


### Cliente:

    cadastrar
    http://{ambiente desejado}/clientes    
    parâmetros enviado via Body(Json)
    Ex:
        {
            "nomeCompleto":"Nome de pessoa 1",
            "sexo":"MASCULINO",
            "dataNascimento":"01/01/2000",
            "idCidade":1
        }
---
    consultar todos
    Método GET
    http://{ambiente desejado}/clientes
---
    consultar por nome do cliente
    Método GET
    http://{ambiente desejado}/clientes?nome={nome}  
---
    consultar por id
    Método GET
    http://{ambiente desejado}/clientes?id={id}
---
    consultar por id e nome do cliente
    Método GET
    http://{ambiente desejado}/clientes?id={id}&nome={nome}
---
    atualizar nome do cliente
    Méthodo PATCH
    http://{ambiente desejado}/clientes?id={id}/nome
    O texto deve ser enviado no Body (Json)
    Ex:
        { 
            "nome" : "Nome de pessoa 2" 
        }
---
    excluir cliente
    Método DELETE
    http://{ambiente desejado}/clientes?id={id}

---
### Dados desenvolvedor
> Desenvolvido por Alan Fernandes Meneguim
> 
> alan.meneguim@gmail.com
> 
> https://github.com/meneguim
