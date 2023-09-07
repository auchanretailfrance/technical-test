<script>
import axios from 'axios'

export default {
  name: 'InterviewDashboard',
  data () {
    return {
      interviews: [],
      newInterview: {}
    }
  },

  methods: {
    loadInterviews () {
      axios.get('/interview')
          .then((response) => {
            this.interviews = response.data
          })
    },

    addInterview () {
      axios.post('/interview', this.newInterview)
          .then((response) => {
            const addedInterview = response.data
            this.interviews.push(addedInterview)
          })
      this.newInterview = {}
    }
  },

  computed: {
    atLeastOneInterviewFound () {
      return this.interviews && this.interviews.length > 0
    }
  },

  mounted() {
    this.loadInterviews()
  }
}
</script>

<template>
  <div class="interview-bloc">
    <h1>All interviews</h1>
    <div v-if="atLeastOneInterviewFound">
      <table>
        <tr>
          <th>Index</th>
          <th>Id</th>
          <th>Interviewer</th>
          <th>Interviewee</th>
          <th>Approved ?</th>
        </tr>
        <tr v-for="(interview, index) in interviews" :key="interview.id">
          <td>{{index}}</td>
          <td>{{interview.id}}</td>
          <td>{{interview.interviewer}}</td>
          <td>{{interview.interviewee}}</td>
          <td>{{interview.approved}}</td>
        </tr>
      </table>
    </div>
    <div v-else>No interview found!</div>
  </div>

  <div class="separator"></div>

  <div class="interview-bloc">
    <h1>Add a new interview:</h1>
    <label for="interviewer-name">Interviewer name:</label><input type="text" id="interviewer-name" v-model="newInterview.interviewer" placeholder="Type the interviewer name"/><br/>
    <label for="interviewee-name">Interviewee name:</label><input type="text" id="interviewee-name" v-model="newInterview.interviewee" placeholder="Type the interviewee name"/><br/>
    <label for="approved-interview">Has been approved ?</label><input type="checkbox" id="approved-interview" v-model="newInterview.approved"/><br/>
    <button @click="addInterview">Create new interview</button>
  </div>

</template>

<style scoped>

table, th, td {
  border: 1px solid;
}

table {
  width: 100%;
  border-collapse: collapse;
}

.interview-bloc {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

.separator {
  margin: 20px;
}

input[type=text] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  width: 100%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>