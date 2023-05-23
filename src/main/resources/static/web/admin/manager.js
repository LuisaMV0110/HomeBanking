const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      loansInfo: [],
      name: "",
      maxAmount: "",
      payments: [],
      payment: undefined,
      interest: "",
    };
  },
  created() {
    this.loadData();
  },
  methods: {
loadData() {
        axios
        .get("http://localhost:8080/api/loans")
        .then((response) => {
          this.loansInfo = response.data.sort((x, y) => y.interest - x.interest);;

        }).catch((err) => console.log(err));
      },
      formatCurrency(balance) {
        let options = { style: "currency", currency: "USD" };
        let numberFormat = new Intl.NumberFormat("en-US", options);
        return numberFormat.format(balance);
      },
      addLoan(){
        Swal.fire({
          title: "Are you sure to create this " + this.name +" loan?",
          icon: "info",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Yes, I'm sure!",
        })
          .then((result) => {
            if (result.isConfirmed) {
              axios
                .post('/api/manager/loans',{name:this.name,maxAmount:this.maxAmount, payments: this.payments, interest:this.interest} )
                .then((response) => (window.location.href = "/web/admin/manager.html"))
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
      addPayment(){
        this.payments.push(this.payment);
      }
    },


})
.mount("#app");

