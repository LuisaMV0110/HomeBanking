const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      id: "",
      data: "",
      cardId: [],
      debit: [],
      credit: [],
      type: "",
      color: "",
      setType: [],
      selectType: "",
      selectColor: "",
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
          this.cardId = this.data.cards;
          this.debit = this.cardId.filter((card) => card.type == "DEBIT");
          this.credit = this.cardId.filter((card) => card.type == "CREDIT");
        })
        .catch((err) => console.log(err));
    },
    previewCardType() {
      if (this.selectType == "CREDIT") {
        this.type = "CREDIT";
      } else if (this.selectType == "DEBIT") {
        this.type = "DEBIT";
      }
    },
    previewCardColor() {
      if (this.selectColor == "TITANIUM") {
        this.color = "TITANIUM";
      } else if (this.selectColor == "GOLD") {
        this.color = "GOLD";
      } else if (this.selectColor == "SILVER") {
        this.color = "SILVER";
      }
    },
    createCards() {
      Swal.fire({
        title: "Are you sure to create a new Card?",
        text: "You can only create 6 cards!",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, create it!",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .post(
                "/api/clients/current/cards",
                `type=${this.type}&color=${this.color}`
              )
              .then((response) => (window.location.href = "/web/cards.html"))
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
}).mount("#app");
window.onload = function(){
  $('#loader').fadeOut();
  $('body').removeClass('hidden');
}
