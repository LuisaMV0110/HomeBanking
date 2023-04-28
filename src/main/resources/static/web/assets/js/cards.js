const { createApp } = Vue;

const app = createApp({
    data(){
        return{
        id: '',
        data: '',
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
        .get('http://localhost:8080/api/clients/current')
        .then(response => {
            this.data = response.data
            this.cardId = this.data.cards
            this.debit = this.cardId.filter(card => card.type == "DEBIT");
            this.credit = this.cardId.filter(card => card.type == "CREDIT");
    }).catch(err => console.log(err));
    },
    signOut() {
        Swal.fire({
            title: 'Are you sure to logout?',
            icon: 'info',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        }).then((result) => {
            if (result.isConfirmed) {
                axios.post('/api/logout')
                    .then(response => window.location.href = "/web/index.html")
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data,
                        })
                    })
            }
        })
            .catch(error => console.log(error))
    },
}
})
.mount('#app');
