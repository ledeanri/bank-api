<template>
  <div id="app">
  <nav>
    <div class="nav-wrapper blue darken-1">
      <a href="#" class="brand-logo center">Banco Front</a>
    </div>
  </nav>
  <div>
  <div class="container">
    <tabs class="nav nav-tabs">
        <tab name="Consultar Saldo">
        <ul>
          <li v-for="(erro, index) of errors" :key="index">
            Campo <b>{{ erro.field }}</b> - {{ erro.defaultMessage }}
          </li>
        </ul>
        <form @submit.prevent="saldo">
            <label>Número da Conta</label>
            <input type="text" placeholder="Número da Conta" v-model="conta.numeroConta"/>
            <label>Senha</label>
            <input type="password" placeholder="Senha" v-model="conta.senha"/>

            <button class="waves-effect waves-light btn-small">Consultar Saldo<i class="material-icons left">save</i></button>

        </form>

        </tab>
        <tab name="Realizar Depósito">
            <ul>
              <li v-for="(erro, index) of errors" :key="index">
                Campo <b>{{ erro.field }}</b> - {{ erro.defaultMessage }}
              </li>
            </ul>
            <form @submit.prevent="deposito">
                <label>Número da Conta</label>
                <input type="text" placeholder="Número da Conta" v-model="conta.numeroConta"/>
                <label>Valor</label>
                <input type="number" placeholder="valor" v-model="conta.valorPassado"/>

                <button class="waves-effect waves-light btn-small">Realizar deposito<i class="material-icons left">save</i></button>

            </form>
        </tab>
        <tab name="Realizar Saque">
          <ul>
            <li v-for="(erro, index) of errors" :key="index">
              Campo <b>{{ erro.field }}</b> - {{ erro.defaultMessage }}
            </li>
          </ul>
          <form @submit.prevent="saque">
              <label>Número da Conta</label>
              <input type="text" placeholder="Número da Conta" v-model="conta.numeroConta"/>
              <label>Valor</label>
              <input type="number" placeholder="valor" v-model="conta.valorPassado"/>
              <label>Senha</label>
              <input type="password" placeholder="Senha" v-model="conta.senha"/>

              <button class="waves-effect waves-light btn-small">Realizar saque<i class="material-icons left">save</i></button>

          </form>
        </tab>
    </tabs>
  </div>
  </div>

  </div>
</template>
<script>
import Conta from './services/contas'
  export default {
    data(){
      return {
        conta: {
          numeroConta: '',
          senha: '',
          valorPassado: 0
        },
        errors: []
      }
    },
    methods:{
      saldo(){
        Conta.saldo(this.conta).then(resposta => {
          this.conta = {}
          alert("Saldo: " + resposta.data)
          this.errors = []
        }).catch(e => {
          /* eslint-disable no-console */
          console.log(e)
          /* eslint-disable no-console */
          this.errors = e.response.data.errors
        })
      },
      deposito(){
        Conta.deposito(this.conta).then(resposta => {
          resposta.data
          this.conta = {}
          alert("Deposito realizado com sucesso.")
          this.errors = []
        }).catch(e => {
          this.errors = e.response.data.errors
        })
      },
      saque(){
        Conta.saque(this.conta).then(resposta => {
          resposta.data
          this.conta = {}
          alert("Saque realizado com sucesso")
          this.errors = []
        }).catch(e => {
          this.errors = e.response.data.errors
        })
      }
    }

  }
</script>
