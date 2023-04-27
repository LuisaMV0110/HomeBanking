const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        data: '',
        cardId:[],
        debit:[],
        credit:[],
        type:'',
        color: '',
        setType:[],
        selectType:'',
        selectColor:''
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
            this.cardId = this.data.cards
            this.debit = this.cardId.filter(card => card.type == "DEBIT");
            this.credit = this.cardId.filter(card => card.type == "CREDIT");
    }).catch(err => console.log(err));
    },
    previewCardType(){        
    if(this.selectType == 1){
        this.type = "CREDIT"
    }
    else if(this.selectType == 2){
        this.type = "DEBIT"
    }

},
    previewCardColor(){
        if(this.selectColor == 1){
            this.color = "TITANIUM"
        }
        else if(this.selectColor == 2){
            this.color = "GOLD"
        }
        else if(this.selectColor == 3){
            this.color = "SILVER"
        }
    },
    createCards(){
            axios.post('/api/clients/current/cards',`type=${this.type}&color=${this.color}`)
            .then(response => window.location.href="/web/cards.html")
            .catch(error => console.log(error)) 
        },
    signOut() {
        axios.post('/api/logout')
        .then(response => window.location.href="/web/index.html")
        .catch(error => console.log(error));
    }
}/* /api/clients/current/cards */
})
.mount('#app');