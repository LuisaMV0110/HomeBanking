const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        number: '',
        creationDate: '',
        balance: '',
        firstName: '',
        lastName: '',
        params: '',
        accountId: '',
        accountId2:[],
        loans: [],
        name:'',
        amount: '',
        payments:''
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/clients'+ this.id)
        .then(response => {
            this.params = new URLSearchParams(location.search);
            this.id = this.params.get('id');
            this.accountId = response.data.find(account=> account.id == this.id);
            this.accountId2 = this.accountId.accounts.sort((x,y) => x.id - y.id);
            this.loans = this.accountId.loans.sort((x,y) => x.id - y.id);
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