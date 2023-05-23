const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      id: "",
      data: "",
      data2:"",
      accounts:[],
      loans: [],
      payments:"",
      typeLoan:"",
      paymentsFilter:[],
      paymentsSort:[],
      amount:"",
      destinateAccount:""
    };
  },
  created() {
    this.loadData();
    this.getLoans();
  },
  methods: {
    loadData() {
      axios
        .get("http://localhost:8080/api/clients/current")
        .then((response) => {
          this.data = response.data;
          this.accounts = this.data.accounts.sort((x, y) => x.id - y.id);
        })
        .catch((err) => console.log(err));
    },
    formatCurrency(balance) {
      let options = { style: "currency", currency: "USD" };
      let numberFormat = new Intl.NumberFormat("en-US", options);
      return numberFormat.format(balance);
    },
    getLoans(){
        axios
        .get("http://localhost:8080/api/loans")
        .then((response) => {
          this.loans = response.data;
        })
        .catch((err) => console.log(err));
    },
    filterPayments(){
        this.paymentsFilter = this.loans.filter(loan => {
            return this.typeLoan.includes(loan.name);
        })[0];
        this.paymentsSort = this.paymentsFilter.payments.sort((x, y) => x - y)
    },
    addLoans() {
      Swal.fire({
        title: "Are you sure to apply at this loan " + this.typeLoan +" ?",
        text: "You will not be able to apply for another of this type until you pay the debt in full",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, I'm sure!",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .post('/api/loans',{loanID:this.paymentsFilter.id,amount:this.amount, payments: this.payments, destinateAccount:this.destinateAccount} )
              .then((response) => (window.location.href = "/web/accounts.html"))
              .catch((error) => {
                Swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: error.response.data,
                });
              });
          }
        })
        .catch((error) => console.log(error));
    },
    signOut() {
      Swal.fire({
        title: "Are you sure to logout?",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes!",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .post("/api/logout")
              .then((response) => (window.location.href = "/web/index.html"))
              .catch((error) => {
                Swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: error.response.data,
                });
              });
          }
        })
        .catch((error) => console.log(error));
    },
  },
  computed: {

}
}).mount("#app");
window.onload = function(){
  $('#loader').fadeOut();
  $('body').removeClass('hidden');
}