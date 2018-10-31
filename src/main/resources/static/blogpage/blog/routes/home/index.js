var express = require('express');
var router = express.Router();
const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017/blog";
/* GET home page. */
router.get('/', function(req, res, next) {
	MongoClient.connect(url,(err,db) => {
		if (err) throw err;
		let cats = db.collection('cats');
		cats.find().toArray( (err,res1) => {
			if (err) throw err;
			//取文章
			let posts = db.collection('posts');
			posts.find().sort({time : -1}).limit(5).toArray( (err,res2) => {
				if (err) throw err;
				console.log(res1);
				console.log(res2);	
				res.render('home/index', {cats : res1, posts : res2});
			});
		} );
	});
  	
});

module.exports = router;
