const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      id: "",
      data: "",
      accounts: [],
      accountType: "",
      loans: [],
      cards: [],
      accountsActive: [],
      cardActive: [],
      totalBalance: null,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("http://localhost:8080/api/clients/current")
        .then((response) => {
          this.data = response.data;
          this.cards = this.data.cards;
          this.cardActive = this.cards.filter((card) => card.cardActive == true);
          this.accounts = this.data.accounts.sort((x, y) => x.id - y.id);
          this.accountsActive = this.accounts.filter((account) => account.accountActive == true);
          this.loans = this.data.loans.sort((x, y) => x.id - y.id);
          for (account of this.accounts) {
            this.totalBalance += account.balance;
          }
        })
        .catch((err) => console.log(err));
    },
    formatCurrency(balance) {
      let options = { style: "currency", currency: "USD" };
      let numberFormat = new Intl.NumberFormat("en-US", options);
      return numberFormat.format(balance);
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

    addAccount() {
              Swal.fire({
                title: "Are you sure to create a new account?",
                text: "You can only create 3 accounts!",
                icon: "info",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, create it!",
              })
                .then((result) => {
                  if (result.isConfirmed) {
                    axios
                      .post("/api/clients/current/accounts",`accountType=${this.accountType}`)
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
      deleteAccount(id){
        Swal.fire({
          title: "Are you sure to delete this account?",
          icon: "info",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Yes!",
        })
          .then((result) => {
            if (result.isConfirmed) {
              axios
                .put(`/api/accounts/${id}`)
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
    },
  }).mount("#app");
