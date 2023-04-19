const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: new URLSearchParams(location.search).get('id'),
        number: '',
        creationDate: '',
        balance: '',
        firstName: '',
        lastName: '',
        clientId: '',
        accountId:[],
        loans: [],
        name:'',
        amount: '',
        payments:'',
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/clients/'+ this.id)
        .then(response => {
            this.clientId = response.data
            this.accountId = this.clientId.accounts.sort((x,y) => x.id - y.id);
            this.loans = this.clientId.loans.sort((x,y) => x.id - y.id);
    }).catch(err => console.log(err));
    },
    formatCurrency(balance){
        let options = { style: 'currency', currency: 'USD' };
        let numberFormat = new Intl.NumberFormat('en-US', options);
        return numberFormat.format(balance);
},   
}
})
.mount('#app');