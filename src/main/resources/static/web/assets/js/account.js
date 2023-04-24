const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        accounts: '',
        transactions: []
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/accounts/current')
        .then(response => {
            this.accounts = response.data;
            this.transactions = this.accountId.transactions.sort((x,y) => y.id - x.id);
    }).catch(err => console.log(err));
    },
    formatCurrency(amount){
        let options = { style: 'currency', currency: 'USD' };
        let numberFormat = new Intl.NumberFormat('en-US', options);
        return numberFormat.format(amount);
}, 
}})

.mount('#app');