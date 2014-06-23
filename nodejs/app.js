var 	express = require('express'),
	version = require('./version_compare'),
	models = require('./models'),
	db = models.db;

// Set up middleware

var app = express();
app.configure(function() {
	app.use(express.bodyParser());
});

// Routes

app.get('/', function(req, res) {
	models.Vuln.findAll().success(function(v) {
		res.json(v);
	});
});

app.post('/enroll', function(req, res) {
	var p = req.body;
	var vulns = [];

	if (!("kernel.version" in p) || !("os.arch" in p)) {
		res.statusCode = 400;
		return res.json('Syntax incorrect');
	}

	var arch = p["os.arch"].toLowerCase();
	if (!~arch.indexOf("arm")) {
		return res.json('Architecture unsupported');
	}

	models.Vuln.findAll({where: {
		affects: {like: '%|' + p["os.version"] + '|%'}, 
		target: "kernel"
	}}).success(function(v) {
		vulns.push(v)
	});

	return res.json({"vulns": vulns});
});

// Go go go

app.listen(process.env.PORT || 4040);
