<template>
        <div class="row">
        <div class='col-lg-4  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">First name</span>
                <input type="text" class="form-control" placeholder="firstName" aria-label="firstName"
                    aria-describedby="basic-addon1" v-model.trim="firstName">
            </div>
        </div>
        <div class='col-lg-4  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">Last name</span>
                <input type="text" class="form-control" placeholder="lastName" aria-label="lastName"
                    aria-describedby="basic-addon1" v-model.trim="lastName">
            </div>
        </div>
        <div class='col-lg-4  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">Birthdate</span>
                <input type="number" class="form-control" placeholder="birthdate" aria-label="birthdatee"
                    aria-describedby="basic-addon1" v-model.number="birthdate">
            </div>
        </div>

    </div>
    <div class = "row">
        <div class="col">
            <button type="button" class="btn btn-success" @click="sendBook()" :disabled="!isValid">Create</button>
        </div>
        
    </div>
</template>
  
<script>
let api = process.env.VUE_APP_SERVE_URL


export default {
    name: 'AddAuthor',
    data() {
        return {
            firstName:"",
            birthdate:2000,
            title: "",
            sucessMessage: ""
        }
    },
    methods: {
        getAuthors() {
            console.log(api)
            this.axios.get(`${api}/library/authors`)
                .then(
                    res => {
                        this.authors = res.data
                        this.selectAuthor = this.authors.at(0).id
                    }
                )
        },
        sendBook(){
            if (this.isValid){
                this.axios.post(`${api}/library/author`, {
                    lastName : this.lastName,
                    firstName: this.firstName,
                    birthdate: this.birthdate
                }).then(
                    () => alert("Author saved sucessfully")
                )
            }
        },
    },
    computed: {
        // note we are not passing an array, just one store after the other
        // each store will be accessible as its id + 'Store'
        isValid(){
            return this.selectAuthor != 0 && this.firstName!="" && this.birthdate!=0 && this.lastName!=""
        }
    },
}
</script>
  
<style></style>
  