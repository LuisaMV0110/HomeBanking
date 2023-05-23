const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      id: "",
      data: "",
      cardId: [],
      cardActive: [],
      debit: [],
      credit: [],
      actDate : new Date().toLocaleDateString().split(",")[0].split("/").reverse().join("-"),
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
          this.cardActive = this.cardId.filter((card) => card.cardActive == true);
          this.debit = this.cardActive.filter((card) => card.type == "DEBIT");
          this.credit = this.cardActive.filter((card) => card.type == "CREDIT");
        })
        .catch((err) => console.log(err));
    },
    deleteCard(id){
      Swal.fire({
        title: "Are you sure to delete this card?",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes!",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .put(`/api/clients/current/cards/${id}`)
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
