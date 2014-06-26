// Using in-memory SQLite database with Sequelize wrapper.
// Allows for easy querying and storage.

var Sequelize = require('sequelize');
var sq = new Sequelize('mdm', null, null, {dialect: 'sqlite'});

var Vuln = sq.define('Vuln', {
	"CVE": { type: Sequelize.STRING, primaryKey: true },
	"description": Sequelize.STRING,
	"type": Sequelize.ENUM('exec', 'info', 'dos', 'priv'), // and others
	"target": Sequelize.ENUM('kernel', 'runtime'),
	"affects": Sequelize.STRING,
	"exploit": Sequelize.STRING,
	"exploit_entry": Sequelize.STRING,
	"patch": Sequelize.STRING,
	"patch_entry": Sequelize.STRING
});

module.exports = {db: sq, Vuln: Vuln};

Vuln.sync()
Vuln.create({
	"CVE": "CVE-2014-1939", 
	"description": "java/android/webkit/BrowserFrame.java vulnerability in searchBoxJavaBridge_", 
	"type": "exec", 
	"target": "runtime", 
	"affects": "|4.0|4.0.1|4.0.2|4.0.3|4.0.4|4.1|4.1.2|4.2|4.2.1|4.2.2|4.3|4.3.1|"
});

Vuln.create({
	"CVE": "CVE-2013-6282", 
	"description": "get_user and put_user kernel functions on ARM vulnerability", 
	"type": "exec", 
	"target": "kernel", 
	"affects": "|3.0 RC4|3.0 RC1|3.0 RC5|3.0 RC2|3.0 RC6|3.0 RC3|3.0 RC7|3.0.1|3.0.2|3.0.3|3.0.4|3.0.5|3.0.6|3.0.7|3.0.8|3.0.9|3.0.10|3.0.11|3.0.12|3.0.13|3.0.14|3.0.15|3.0.16|3.0.17|3.0.18|3.0.19|3.0.20|3.0.21|3.0.22|3.0.23|3.0.24|3.0.25|3.0.26|3.0.27|3.0.28|3.0.29|3.0.30|3.0.31|3.0.32|3.0.33|3.0.34|3.0.35|3.0.36|3.0.37|3.0.38|3.0.39|3.0.40|3.0.41|3.0.42|3.0.43|3.0.44|3.0.45|3.0.46|3.0.47|3.0.48|3.0.49|3.0.50|3.0.51|3.0.52|3.0.53|3.0.54|3.0.55|3.0.56|3.0.57|3.0.58|3.0.59|3.0.60|3.0.61|3.0.62|3.0.63|3.0.64|3.0.65|3.0.66|3.0.67|3.0.68|3.1 RC3|3.1 RC4|3.1|3.1 RC1|3.1 RC2|3.1.1|3.1.2|3.1.3|3.1.4|3.1.5|3.1.6|3.1.7|3.1.8|3.1.9|3.1.10|3.2 RC7|3.2 RC2|3.2 RC3|3.2 RC4|3.2 RC5|3.2 RC6|3.2|3.2.1|3.2.2|3.2.3|3.2.4|3.2.5|3.2.6|3.2.7|3.2.8|3.2.9|3.2.10|3.2.11|3.2.12|3.2.13|3.2.14|3.2.15|3.2.16|3.2.17|3.2.18|3.2.19|3.2.20|3.2.21|3.2.22|3.2.23|3.2.24|3.2.25|3.2.26|3.2.27|3.2.28|3.2.29|3.2.30|3.3 RC6|3.3 RC1|3.3 RC7|3.3 RC2|3.3 RC3|3.3 RC4|3.3 RC5|3.3|3.3.1|3.3.2|3.3.3|3.3.4|3.3.5|3.3.6|3.3.7|3.3.8|3.4 RC6|3.4 RC1|3.4 RC7|3.4 RC2|3.4 RC3|3.4 RC4|3.4 RC5|3.4|3.4.1|3.4.2|3.4.3|3.4.4|3.4.5|3.4.6|3.4.7|3.4.8|3.4.9|3.4.10|3.4.11|3.4.12|3.4.13|3.4.14|3.4.15|3.4.16|3.4.17|3.4.18|3.4.19|3.4.20|3.4.21|3.4.22|3.4.23|3.4.24|3.4.25|3.4.26|3.4.27|3.4.28|3.4.29|3.4.30|3.4.31|3.4.32|3.5.1|3.5.2|3.5.3|3.5.4|",
	"exploit": "exp_cve_2013_6282.so",
	"exploit_entry": "nativeExp1",
	"patch": "pat_cve_2013_6282.so",
	"patch_entry": "nativePat1"
});

