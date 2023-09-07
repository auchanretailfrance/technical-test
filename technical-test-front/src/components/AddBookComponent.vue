<template>
    <div class="row">
        <div class='col-lg-6 col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">Author</span>
                <select class="form-select" v-model="selectAuthor">
                    <option disabled value="">Please select one</option>
                    <option v-for="option in authors" :key="option.id" :value="option.id">
                        {{ option.firstName }} {{ option.lastName }}
                    </option>
                </select>
            </div>
        </div>
        <div class='col-lg-6  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">isbn</span>
                <input type="text" class="form-control" placeholder="isbn" aria-label="isbn"
                    aria-describedby="basic-addon1" v-model.trim="isbn">
            </div>
        </div>
        <div class='col-lg-6  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">Release date</span>
                <input type="number" class="form-control" placeholder="Release date" aria-label="Release date"
                    aria-describedby="basic-addon1" v-model.number="releaseDate">
            </div>
        </div>
        <div class='col-lg-6  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">Title</span>
                <input type="text" class="form-control" placeholder="Title" aria-label="Title"
                    aria-describedby="basic-addon1" v-model.trim="title">
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
    name: 'AddBook',
    beforeMount() {
        this.getAuthors()
    },
    data() {
        return {
            authors: [],
            selectAuthor: 0,
            isbn:"",
            releaseDate:2000,
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
                this.axios.post(`${api}/library/book`, {
                    isbn : this.isbn,
                    title: this.title,
                    releaseDate: this.releaseDate,
                    author: this.selectAuthor
                }).then(
                    () => alert("Book saved sucessfully")
                )
            }
        },
    },
    computed: {
        // note we are not passing an array, just one store after the other
        // each store will be accessible as its id + 'Store'
        isValid(){
            return this.selectAuthor != 0 && this.isbn!="" && this.releaseDate!=0 && this.title!=""
        }
    },

}
</script>
  
<style></style>