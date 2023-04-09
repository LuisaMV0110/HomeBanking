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
        accountId: []
        }
    },
    created(){
    this.loadData()
    },
    methods:{
loadData(){
        axios
        .get('http://localhost:8080/api/clients')
        .then(response => {
            this.params = new URLSearchParams(location.search);
            this.id = this.params.get('id');
            this.accountId = response.data.find(account=> account.id == this.id);
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