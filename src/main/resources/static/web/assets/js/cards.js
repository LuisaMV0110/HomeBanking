const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: new URLSearchParams(location.search).get('id'),
        clientId: '',
        type:'',
        color:'',
        fromDate: '',
        thruDate:'',
        cardHolder: '',
        number:'',
        cvv:'',
        cardId:[],
        debit:[],
        credit:[],
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
            this.cardId = this.clientId.cards
            this.debit = this.cardId.filter(card => card.type == "DEBIT");
            this.credit = this.cardId.filter(card => card.type == "CREDIT");
    }).catch(err => console.log(err));
    },
}
})
.mount('#app');
