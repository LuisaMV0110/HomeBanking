const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: new URLSearchParams(location.search).get('id'),
        type: '',
        date: '',
        amount: '',
        description: '',
        accountId: '',
        transactions: []
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/accounts/' + this.id)
        .then(response => {
            this.accountId = response.data;
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
/*     transactionClass(type){
        return `transaction-row ${type=="CREDIT"?"CREDIT":"DEBIT"}`
    }, */