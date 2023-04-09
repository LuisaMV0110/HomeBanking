const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        type: '',
        date: '',
        amount: '',
        description: '',
        params: '',
        transactionId: []
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/accounts')
        .then(response => {
            this.params = new URLSearchParams(location.search);
            this.id = this.params.get('id');
            this.transactionId = response.data.find(transaction=> transaction.id == this.id);
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