const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      clientsInfo: [],
      firstName: "",
      lastName: "",
      email: "",
      id: "",
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    async loadData() {
      try {
        axios.get("http://localhost:8080/rest/clients").then((response) => {
          this.clientsInfo = response.data._embedded.clients;
        });
      } catch {
        (err) => console.log(err);
      }
    },
    async postClient() {
      try {
        axios
          .post("http://localhost:8080/rest/clients", {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
          })
          .then((response) => {
            this.loadData();
          });
      } catch {
        (err) => console.log(err);
      }
    },
    async addClient() {
      this.postClient();
    },
  },
}).mount("#app");
