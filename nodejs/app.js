var 	express = require('express'),
	version = require('./version_compare'),
	models = require('./models'),
	db = models.db;

// Set up middleware

var app = express();
app.use(express.bodyParser());
app.use("/files", express.static(__dirname + "/files"));

// Routes

app.get('/', function(req, res) {
	res.send("ExpatMDM");
});

app.post('/enroll', function(req, res) {
	var p = req.body;

	if (!("os.version" in p) || !("os.arch" in p)) {
		res.statusCode = 400;
		return res.json('Syntax incorrect');
	}

	var arch = p["os.arch"].toLowerCase();
	var kernel = p["os.version"].split("-")[0];

	if (!~arch.indexOf("arm")) {
		return res.json('Architecture unsupported');
	}

	models.Vuln.findAll({where: {
		affects: {like: '%|' + kernel + '|%'}, 
		target: "kernel"
	}}).success(function(v) {
		return res.json(v);
	});
});

// Go go go

app.listen(process.env.PORT || 4040);
