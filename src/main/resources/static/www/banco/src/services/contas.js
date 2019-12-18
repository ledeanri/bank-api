import { http } from './config'
import Vue from 'vue';
import {Tabs, Tab} from 'vue-tabs-component';

Vue.component('tabs', Tabs);
Vue.component('tab', Tab);

export default {
  saldo:(conta) => {
    return http.post('saldo', conta);
  },
  deposito:(conta) => {
    return http.put('deposito', conta);
  },
  saque:(conta) => {
    return http.put('saque', conta);
  }
}
