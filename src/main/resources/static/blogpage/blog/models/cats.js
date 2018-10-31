//引入mongodb模块
const MongoClient = require("mongodb").MongoClient;

const config = require("../config.js");

const url = `mongodb://${config.host}:${config.port}/${config.dbname}`;
//定义构造器
function Cats(title,order){
	this.title = title;
	this.order = order;
}
//定义getCats方法，获取分类
Cats.prototype.getCats = function(callback){
	MongoClient.connect(url,(err,db) => {
		if (err) return callback(err);
		let cats = db.collection('cats');
		cats.find().toArray( (err,result) => {
			return callback(err,result);
		});
	});
}
//插入分类操作
Cats.prototype.addCats = function(callback){
	MongoClient.connect(url,(err,db) => {
    	if (err) return callback(err);
   		let cats = db.collection('cats'); //获取集合
    	cats.insert({title : this.title, order : this.order},(err,result) => {
	    	return callback(err,result);
    	});
  	});
}

//导出模块
module.exports = Cats;