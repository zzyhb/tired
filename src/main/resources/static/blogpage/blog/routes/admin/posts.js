var express = require('express');
var router = express.Router();
const Objectid = require('objectid');
const multiparty = require("multiparty");
const fs = require('fs');
const path = require('path');
//载入cats模型
const Cats = require("../../models/cats");
const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017/blog";
/* GET posts page. */
router.get('/', function(req, res, next) {
  //获取数据
  MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    const posts = db.collection('posts');
    posts.find().toArray( (err,result) => {
      if (err) throw err;
       res.render('admin/article_list',{posts : result});
    });
  });
});

//载入添加文章表单页面
router.get('/add', function(req, res, next) {
  //获取所有的分类数据
  /*MongoClient.connect(url,(err,db) => {
    if (err) throw err;
    let cats = db.collection('cats');
    cats.find().toArray((err,result) => {
      console.log(result);
      res.render('admin/article_add',{cats: result});
    });
  });*/
  //实例化一个cats模型
  const cats = new Cats();
  //调用模型的getCats方法
  cats.getCats((err,result) => {
    if(err) throw err;
     res.render('admin/article_add',{cats: result});
  });
});
router.post('/insert', function(req, res, next) {
  //获取表单提交的数据
/*  console.log(req.body);
  let cat = req.body.cat;
  let subject = req.body.subject;
  let summary = req.body.summary;
  let content = req.body.content;*/
  //实例化一个form对象
  let tmp = path.join(__dirname,"../../public/tmp")
  const form = new multiparty.Form({uploadDir:tmp});
  form.parse(req,(err,fields,files) => {
    console.log(fields);
    console.log(files);
    //将图片从临时目录转移到指定目录
    let oldPath = files.cover[0].path;
    // console.log(__dirname);
    let newPath = path.join(__dirname,"../../public/uploads",files.cover[0].originalFilename);
    // let filename = files.cover[0].originalFilename;
    fs.rename(oldPath,newPath,(err) => {
      if (err) throw err;
      //保存成功
      //博客对象
      let article = {
        cat : fields.cat[0],
        subject : fields.subject[0],
        summary : fields.summary[0],
        content : fields.content[0],
        time : new Date(),
        count : Math.ceil(Math.random() * 100),
        cover :   path.join('uploads',files.cover[0].originalFilename) //相对路径
      }
      //入库
      MongoClient.connect(url,(err,db) => {
        if (err) throw err;
        let posts = db.collection('posts');
        posts.insert(article,(err,result) => {
          if (err) {
            res.render("admin/message",{msg : "添加博客失败"});
          } else {
            res.render("admin/message",{msg : "添加博客成功"});
          }
        });
      });
    });
  });
  //博客对象
 /* let article = {
    cat : cat,
    subject : subject,
    summary : summary,
    content : content,
    time : new Date(),
    count : Math.ceil(Math.random() * 100)
  }*/
 

});

router.get('/edit', function(req, res, next) {
   res.render('admin/article_edit',{});
});
router.post('/update', function(req, res, next) {
  
});
router.get('/delete', function(req, res, next) {
 
});
module.exports = router;
