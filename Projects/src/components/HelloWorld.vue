<template>
  <div class="hello">
    <!-- <h1 class = "fontverd title">{{ msg }}</h1> -->
    <svg width = "1300" height = "170">
      <rect width="1300" height="170" x="-100" y="0" style="fill:rgb(255,0,0);"/>
    </svg><br><br>
    <input type="text" name="firstname" class = "firstletteruppercase fontverd textbox" v-model="user">
    <button value="Submit" class = "button fontverd" v-on:click="update()">Select User</button><br><br><br><br>
    <div class="w600 w3-container container row">
      <h2 class = "fontverd"> Blood Pressure: {{blood_pressure}}</h2><br>
      <trend :data="chartData1" smooth :width="600" :height = "400" stroke="#6d0404"></trend> 
    </div>
    <div class="w600 w3-container container row">
      <h2 class = "fontverd">Histamine Concentration: {{histamine_concentration}}</h2><br>
      <trend :data="chartData2" smooth :width="600" :height = "400" stroke="#6d0404"></trend>
    </div>
    <div class="w600 w3-container container row">
      <h2 class = "fontverd">Core Body Temperature: {{core_body_temperature}}</h2><br>
      <trend :data="chartData3" smooth :width="600" :height = "400" :stroke="['#6d0404']"></trend>
    </div><br><br><br>
    <!-- <h2>Safe</h2></br>  -->
    <div v-if="safe=='true' && !fresh" class = "text firstletteruppercase bold fontroboto">User {{user}} is safe</div>
    <div v-else-if="fresh" class = "text fontroboto">No user selected</div>
    <div v-else><font color = "red" class = "text blinking bold capitalize fontroboto ">WARNING: USER {{user}} IS IN DANGER</font></div>
    <!-- <form action="/action_page.php" text ="enter user id">
    </form>  -->
  </div>
</template>




<script>
import trend from 'vuetrend'
import Vue from 'vue'
Vue.use(trend);
import {HTTP} from '@/components/app-common'
export default {
  name: 'HelloWorld',
  components:{
    trend
  },
  data () {
    return {
      fresh : true,
      chartData1: [0],
      chartData2: [0],
      chartData3: [0],
      user: "",
      safe: "true",
      blood_pressure: 0,
      histamine_concentration: 0,
      core_body_temperature: 0, 
      msg: 'Red Brick'
    }
  },
  methods: {
    update: function() {
      this.fresh = false;
      setInterval(this.getData, 1000)
    },
    getData: function() {
      // var output = {};
      var _this = this;
      console.log(_this.user);
      HTTP.get(String(_this.user) + "/get")
      .then(response => {
        console.log(response.data);
        // _this._data = response.data;
        _this.blood_pressure = parseFloat(response.data.blood_pressure.split(' / ')[0]);
        _this.histamine_concentration = parseFloat(response.data.histamine_concentration);
        _this.core_body_temperature = parseFloat(response.data.core_body_temperature);
        console.log(_this.blood_pressure)
        if(_this.chartData1.length == 30)
          _this.chartData1.shift();
        _this.chartData1.push(_this.blood_pressure);
        if(_this.chartData2.length == 30)
          _this.chartData2.shift();
        _this.chartData2.push(_this.histamine_concentration);
        if(_this.chartData3.length == 30)
          _this.chartData3.shift();
        _this.chartData3.push(_this.core_body_temperature);
        _this.safe = response.data.safe;
        console.log(_this.safe);
        _this.user = _this.user;
        // output = response.data;
      })
      .catch(e => {
        console.log(e);
      // return output;
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
h1 {
  color: #ff0000;
}

.title{
  font-size: 90px;
  font-weight: bold;
}

.text {
    font-size: 25px;
}

.textbox{
  padding: 10px;
  font-size: 25px;
  border-color: #ff0000;
}

.button{
  background-color: #ff0000;
  border-radius: 5px;
  border: none;
  text-align: center;
  text-decoration: none;
  font-size: 30px;
  /* padding: 10px; */
  height: 60px;
  color: white;
  outline-color: white;
}

.container{
  background-color: #edc7c7 ;
  border-radius: 25px;
  padding: 20px;
  height: 500px;
}

.blinking{
    animation:blinkingText 1s infinite;
}
@keyframes blinkingText{
    0%{     color: transparent;    }
    25  %{    color: transparent;    }
    49%{    color:  #ff0000; }
    50%{    color: #ff0000; }
    99%{    color:#ff0000;  }
    100%{   color: #ff0000;    }
}
.bold{
  font-weight: bold;
}

.row{
  display:inline-table;
}

.capitalize
{
  text-transform: uppercase;
}
.firstletteruppercase
{
  text-transform: capitalize
} 

.fontkarla
{
  font-family: "Karla" ;
}

.fontroboto
{
  font-family: "Roboto" , sans-serif ;
}
.fontverdana
{
  font-family: "Verdana" , sans-serif ;
  font-size: 30px;
}
.h50
{
  height: 30px;
}
.w600
{
  width: 600px;
}

</style>
