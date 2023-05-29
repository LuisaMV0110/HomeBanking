const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      id: "",
      data: "",
      accounts:[],
      amount:"",
      description:"",
      initialAccount:"",
      destinateAccount:""
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("/api/clients/current")
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
    addTransfers() {
      Swal.fire({
        title: "Are you sure to make this transfer to " + this.destinateAccount +" ?",
        text: "Verify the destination account number for your security!",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, I'm sure!",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .post(
                "/api/transactions",
                `amount=${this.amount}&description=${this.description}&initialAccount=${this.initialAccount}&destinateAccount=${this.destinateAccount}`
              )
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
    upperCase(){
        this.destinateAccount = this.destinateAccount.toUpperCase();
    }  
}
}).mount("#app");
window.onload = function(){
  $('#loader').fadeOut();
  $('body').removeClass('hidden');
}