var express = require('express');
var app = express();
var cors = require('cors')
var bodyParser = require("body-parser");

app.use(cors())

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

var data = {

}

app.get('/get', function(req, res) {
    res.end(JSON.stringify({
        "Title": "True"
    }))
})

app.get('/:id/get', function(req, res) {
    res.end (JSON.stringify({
        "blood_pressure": data[req.params.id].blood_pressure,
        "histamine_concentration": data[req.params.id].histamine_concentration,
        "core_body_temperature": data[req.params.id].core_body_temperature,
        "safe": data[req.params.id].safe
    }))
})

app.post('/:id/update', function(req, res) {
    data[req.params.id].blood_pressure = req.body.blood_pressure
    data[req.params.id].histamine_concentration = req.body.histamine_concentration
    data[req.params.id].core_body_temperature = req.body.core_body_temperature
    data[req.params.id].safe = req.body.safe
    // res.end(JSON.stringify({
    //     str(req.params.id): 
    // }))
})

app.post('/newId', function(req, res) {
    data[req.body.id] = {
        "blood_pressure": req.body.blood_pressure,
        "histamine_concentration": req.body.histamine_concentration,
        "core_body_temperature": req.body.core_body_temperature,
        "safe": req.body.safe
    }
})

var server = app.listen(8081, function(){
    var host = server.address().address
    var port = server.address().port
    console.log("Example app listening at http://%s:%s", host, port)
})