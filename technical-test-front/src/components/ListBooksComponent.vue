<template>
    <div class="row">
        <div class='col-lg-4 col-12'>
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
        <div class='col-lg-3  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">From year</span>
                <input type="text" class="form-control" placeholder="From year" aria-label="From year"
                    aria-describedby="basic-addon1" v-model.trim="min">
            </div>
        </div>
        <div class='col-lg-3  col-12'>
            <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">To year</span>
                <input type="text" class="form-control" placeholder="To year" aria-label="To year"
                    aria-describedby="basic-addon1" v-model.trim="max">
            </div>
        </div>
        <div class="col-2">
            <button type="button" class="btn btn-success" @click="sendBook()"> Search </button>
        </div>
    </div>
    <div v-if="isTable">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Release date</th>
                    <th scope="col">Handle</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(book, idx) in books" :key="idx">
                    <th scope="row">{{idx}}</th>
                    <td>{{book.title}}</td>
                    <td>{{book.releaseDate}}</td>
                    <td>{{book.author}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
let api = process.env.VUE_APP_SERVE_URL


export default {
    name: 'ListBooks',
    beforeMount() {
        this.getAuthors()
    },
    data() {
        return {
            authors: [],
            selectAuthor: "",
            min: 1950,
            max: 1955,
            books: []
        }
    },
    methods: {
        getAuthors() {
            console.log(api)
            this.axios.get(`${api}/library/authors`)
                .then(
                    res => {
                        this.authors = res.data
                        // this.selectAuthor = this.authors.at(0).id
                    }
                )
        },
        sendBook() {
            if (this.isValid) {
                this.books = []
                this.axios.get(`${api}/library/getByAuthorAndDay`, {
                    params: {
                        author: (this.selectAuthor == "")? null : this.selectAuthor,
                        min: this.min,
                        max: this.max
                    }
                }).then(
                    (res) => {
                        this.books = res.data
                        console.log(this.books)
                    }
                )
            }
        },
    },
    computed: {
        // note we are not passing an array, just one store after the other
        // each store will be accessible as its id + 'Store'
        isValid() {
            return this.min != "" && this.max != ""
        },
        isTable() {
            return this.books.length != 0
        }
    },
}
</script>
  
<style></style>