var express = require('express');
var router = express.Router();

const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017/blog";

//显示登录页面
router.get('/login', function(req, res, next) {
	//判断是否已经登录
	if (req.session.isLogin) {
		res.redirect('/admin');
	} else {
		res.render('admin/login');
	}
 	
});

//处理用户提交动作
router.post("/signin",(req,res) => {
	//获取用户名好密码
	let username = req.body.username;
	let password = req.body.password;

	MongoClient.connect(url,(err,db) => {
		if (err) throw err;
		let user = db.collection('user');
		user.findOne({username : username, password : password}, (err,result) => {
			//判断result是否有值
			if (err) throw err;
			if (result) {
				//用户名和密码正确
				//设置seesion，然后跳转
				req.session.isLogin = 1;
				res.redirect('/admin');
			} else {
				//用户名和密码错误
				res.redirect('/user/login');
			}
		});
	});
});
//注销操作
router.get("/logout",(req,res) => {
	//就是销毁session
	req.session.destroy();
	//需要跳转
	res.redirect('/admin');
});

module.exports = router;
