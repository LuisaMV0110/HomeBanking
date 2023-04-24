const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        data: '',
        accounts:[],
        loans: [],
        totalBalance: null
        }
    },
    created(){
    this.loadData()
    },
    methods:{
    loadData(){
        axios
        .get('http://localhost:8080/api/clients/current')
        .then(response => {
            this.data = response.data
            this.accounts = this.data.accounts.sort((x,y) => x.id - y.id);
            this.loans = this.data.loans.sort((x,y) => x.id - y.id);
            for(account of this.accounts){
                this.totalBalance += account.balance
            }
    }).catch(err => console.log(err));
    },
    formatCurrency(balance){
        let options = { style: 'currency', currency: 'USD' };
        let numberFormat = new Intl.NumberFormat('en-US', options);
        return numberFormat.format(balance);
        
},  
    signOut() {
        axios.post('/api/logout')
        .then(response => window.location.href="/web/index.html")
        .catch(error => console.log(error));
    }
}
})
.mount('#app');