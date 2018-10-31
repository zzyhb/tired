var express = require('express');
var router = express.Router();
const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017/blog";
const Objectid = require('objectid');

const markdown = require('markdown').markdown;

/* GET posts page. */
router.get('/', function(req, res, next) {
	//获取id
	let id = req.query.id;
	MongoClient.connect(url,(err,db) => {
		if (err) throw err;
		let posts = db.collection('posts');
		posts.findOne({_id : Objectid(id)},(err,result) => {
			if (err) throw err;
			// res.send(markdown.toHTML(result.content));
			//对content进行转换
			// result.content = markdown.toHTML(result.content);
			res.render('home/posts',{posts : result});
		});
	});
});

module.exports = router;
