const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      loansInfo: [],
      name: "",
      maxAmount: "",
      payments: [],
      interest: "",
      id: "",
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
    },

})
.mount("#app");

