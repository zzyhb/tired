var express = require('express');
var router = express.Router();

const Objectid = require('objectid');

const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017/blog";

/* GET cats page. */
router.get('/', function(req, res, next) {
  //获取数据
  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats');
    cats.find().toArray((err,result) => {
      console.log(result);
      res.render('admin/category_list',{cats: result});
    });
  });
  
});

router.get('/add', function(req, res, next) {
  // res.render('admin/index');
  // res.send('后台的分类添加');

  res.render('admin/category_add');
});

router.post('/insert', function(req, res, next) {
  //获取表单提交的信息
  console.log(req.body);
  let title = req.body.title;
  let order = req.body.order;

  if (title.trim() == '' || order.trim() == '') {
    res.render('admin/message',{msg : '标题和排序不能为空'});
    return;
  }

  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats'); //获取集合
    cats.insert({title : title, order : order},(err,result) => {
      if (err) {
        // res.send('添加分类失败');
        res.render('admin/message',{msg : '添加分类失败'});
      } else {
        // res.send('添加分类成功');
        res.render('admin/message',{msg : '添加分类成功'})
      }
    });
  });
  // res.send('添加动作');
});


router.get('/edit', function(req, res, next) {
  //获取当前分类的信息
  let id = req.query.id;
  console.log(id);
  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats');
    //按照条件来查询
    cats.findOne({_id : Objectid(id)},(err,result) => {
      if (err) throw err;
      console.log(result);
       res.render("admin/category_edit",{cat : result});
    });
  });

});

router.post('/update', function(req, res, next) {
  //获取表单提交的数据
  let title = req.body.title;
  let order = req.body.order;
  let id = req.body.id;
  console.log(req.body);
  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats');
    cats.update({_id:Objectid(id)},{$set : {title : title, order : order}},(err,db) => {
      if (err) {
        res.render('admin/message',{msg : "更新分类失败"});
      } else {
         res.render('admin/message',{msg : "更新分类成功"});
      }
    });
  });
});

router.get('/delete', function(req, res, next) {
  //获取id
  let id = req.query.id;
  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats');
    cats.remove({_id : Objectid(id)}, (err,result) => {
      if (err) {
        res.render('admin/message',{msg : "删除分类失败"});
      } else {
        res.render('admin/message',{msg : "删除分类成功"});
      }
    });
  });
});


module.exports = router;
